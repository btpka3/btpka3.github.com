"use strict";
const spawn = require('child_process').spawn;
const Q = require("q");
//const ls = spawn('ls', ['-lh', '/usr']);
const exec = require('child_process').exec;
const ls = exec('ls -lh /usr ');
const fs = require('fs');


var G = require("nodegit");

// node-git , git-utils
/*

 # 获取当前工程下所有的用户的提交次数
 git log --after=2015-01-01 --before=2016-03-01 --pretty='%an' | sort | uniq -c | sort -k1 -n -r

 # 获取当前工程下所有的用户名
 git log --after=2015-01-01 --before=2016-03-01 --pretty='%an' | sort -u

 # 统计特定员工增加行数，删除行数（注意：需要排除不统计的文件——比如自动生成的文件）
 git log --author="swx" --after=2015-01-01 --before=2016-03-01 --pretty=tformat: --numstat \
 | gawk '{ add += $1 ; subs += $2 ; loc += $1 - $2 } \
 END { printf "added lines: %s removed lines : %s total lines: %s\n",add,subs,loc }' -


 # 是否有两个parent（是够该commit未rebase）
 git cat-file commit $commitHash |sed -n -r -e '/^parent [a-z0-9]{40}$/p'|wc -l`

 # 判断该 commit 是否包含未merged内容
 git show -U0 $commitHash
 */

//console.log(new Date("2011-10-10T14:48:00+08:00"));
Q.onerror = (err)=> {
    console.log("---Q.onerror : ", err);
};
const configs = [{
    projectName: "qh-domain",
    repoPath: "/home/zll/work/git-repo/kingsilk/qh-domain/",
    commitFrom: new Date("2015-01-01T14:48:00+08:00"),
    commitTo: new Date("2016-03-01T00:00:00+08:00"),
    ignoreFiles: ["src/lib/**/*"], // GLOB
    ignoreUsers: ["sqz"],
    branches: ["refs/heads/master"],
    committerAlias: {
        "chenli": "可可",
        "btpka3": "般若",
        "swx": "唐印",
        "hechengwen": "胡杨",
        "wangjh": "青蒿"
    }
}];

const results = {};

const repoPromies = [];

configs.forEach(cfg => {

    let repoP = G.Repository.open(cfg.repoPath).then(repo => {
        let repoResult = {};
        results[cfg.projectName] = repoResult;
        repoResult.config = cfg;

        let commitPromises = [];
        //let xxxx1 = Q();
        cfg.branches.forEach(branchName => {
            let commitP = repo.getReferenceCommit(branchName).then((firstCommit)=> {
                let defered = Q.defer();
                let eventPromises = [];

                let history = firstCommit.history();

                history.on("commit", onCommit);

                history.on("end", onEnd);

                // Start emitting events.
                history.start();

                return defered.promise;

                function onCommit(commit) {
                    try {
                        if (commit.date() < cfg.commitFrom || commit.date() > cfg.commitTo) {
                            return;
                        }

                        let committer = commit.committer();
                        let committerName = committer.name();
                        let committerAlias = committerName;
                        if (cfg.committerAlias[committerName]) {
                            committerAlias = cfg.committerAlias[committerName];
                        }

                        let stat = repoResult[committerAlias];
                        if (!stat) {
                            stat = {
                                committerAlias: committerAlias,
                                commits: 0,
                                addedLines: 0,
                                deletedLines: 0,
                                unMergedCount: 0,
                                unMergedCommits: [],
                                unRebasedCount: 0,
                                unRebasedCommits: [],
                                firstCommit: null,
                                lastCommit: null
                            };
                            repoResult[committerAlias] = stat;
                        }

                        if (!stat.firstCommit) {
                            stat.firstCommit = commit.sha();
                        }
                        stat.lastCommit = commit.sha();

                        // 统计 提交的commit的次数
                        stat.commits++;

                        let parentsP = commit.getParents().then(function (arrayCommit) {
                            if (arrayCommit.length > 1) {
                                stat.unRebasedCount++;
                                stat.unRebasedCommits.push(commit.sha());
                            }
                        });
                        eventPromises.push(parentsP);

                        let diffP = commit.getDiff().then(arrayDiff => {

                            let patchPromises = [];

                            arrayDiff.forEach(diff => {
                                let patchP = diff.patches().then(function (patches) {
                                    let hunkPromises = [];
                                    patches.forEach(patch => {
                                        // 忽略的文件
                                        if (patch.newFile().path() === "xxx") {
                                            return;
                                        }
                                        let hunkP = patch.hunks().then(function (hunks) {
                                            let linePromises = [];
                                            hunks.forEach(hunk => {
                                                let lineP = hunk.lines().then(function (lines) {
                                                    let r = {
                                                        unMerged: false,
                                                        added: 0,
                                                        deleted: 0
                                                    };

                                                    lines.forEach(line => {

                                                        let lineContent = line.content().trim();
                                                        if (!r.unMerged && (
                                                                lineContent.indexOf(">>>>>>>") === 0 ||
                                                                lineContent.indexOf("<<<<<<<") === 0 ||
                                                                lineContent.indexOf("=======") === 0
                                                            )) {
                                                            r.unMerged = true;
                                                        }
                                                        if ("+" === String.fromCharCode(line.origin())) {
                                                            r.added++;
                                                        } else if ("-" === String.fromCharCode(line.origin())) {
                                                            r.deleted++;
                                                        }
                                                    });

                                                    if (r.unMerged) {
                                                        stat.unMergedCount++;
                                                        stat.unMergedCommits.push(commit.sha());
                                                    }
                                                    stat.addedLines += r.added;
                                                    stat.deletedLines += r.deleted;

                                                    return Q();
                                                });

                                                linePromises.push(lineP);
                                            });
                                            return Q.allSettled(linePromises);
                                        });
                                        hunkPromises.push(hunkP);
                                    });
                                    return Q.allSettled(hunkPromises);
                                });
                                patchPromises.push(patchP);
                            });
                            return Q.allSettled(patchPromises);
                        });
                        eventPromises.push(diffP);
                    } catch (err) {
                        defered.reject(err);
                        history.off(onCommit);
                    }
                }

                function onEnd(commits) {
                    try {
                        defered.resolve(Q.allSettled(eventPromises));
                    } catch (err) {
                        defered.reject(err);
                    }

                }


            });
            commitPromises.push(commitP);
        });
        return Q.allSettled(commitPromises).then(arr => {
            // 注意这里手动判断是否成功，并明确reject，因为Q.allSettled不会调用错误处理。
            for (let r of arr) {
                if (r.state === "rejected") {
                    return Q.reject(r.reason);
                }
            }
        });
    });
    repoPromies.push(repoP);
});
Q.allSettled(repoPromies).then((arr) => {

    arr.forEach(r => {
        if (r.state === "rejected") {
            let err = r.reason;
            console.log(`ERROR  : ${err.message} @ ${err.fileName}:${err.lineNumber}, ${err.stack}`);
        }
    });

    let resultJsonStr  = JSON.stringify(results, null, "  ");
    console.log("================ results = ", JSON.stringify(results, null, "  "));

    fs.writeFile("/tmp/data.json", resultJsonStr, function(err) {
        if(err) {
            return console.log(err);
        }

        console.log("The file was saved!");
    });
});
