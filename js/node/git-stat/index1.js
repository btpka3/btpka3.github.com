"use strict";
const spawn = require('child_process').spawn;
const Q = require("q");
//const ls = spawn('ls', ['-lh', '/usr']);
const exec = require('child_process').exec;
const ls = exec('ls -lh /usr ');

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

var config = [{
    projectName: "qh-domain",
    repoPath: "/home/zll/work/git-repo/kingsilk/qh-domain/",
    commitFrom: "2015-07-10T14:48:00+08:00",
    commitTo: "2016-03-01T00:00:00+08:00",
    ignoreFiles: ["src/lib/**/*"], // GLOB
    ignoreUsers: ["sqz"],
    branches:[""],
    committerAlias: {
        "chenli": "陈丽"
    }
}, {}];


var gitRepoUrl = "/home/zll/work/git-repo/kingsilk/qh-domain/";

G.Repository.open(gitRepoUrl).then(function (repo) {
    console.log("111" + repo);

    let refNames = ["refs/heads/master"];

    ////repo.getReference()
    //repo.getReferences(G.Reference.TYPE.LISTALL).then(function (refs) {
    //    for (let ref of refs) {
    //        if (ref.isBranch() ) {
    //            console.log("111 - " + ref.name());
    //            return Q(ref);
    //        }
    //    }
    //});
    //return repo.getMasterCommit();

    return Q(repo.getReferenceCommit(refNames[0]));


}).then((firstCommitOnMaster)=> {
    // Create a new history event emitter.
    var history = firstCommitOnMaster.history();

    // Create a counter to only show up to 9 entries.
    var count = 0;

    var c = {};

    // Listen for commit events from the history.
    history.on("commit", function (commit) {


        // Disregard commits past 9.
        ++count;
        let committer = commit.committer();
        if (c[committer]) {
            c[committer]++;
        } else {
            c[committer] = 1;
        }


        if (count >= 3) {
            return;
        }

        console.log("=======================================");
        // Show the commit sha.
        console.log("commit " + commit.sha());

        // Display author information.
        console.log("Author:\t" + committer.name() + " <" + committer.email() + ">");

        // Show the commit date.
        console.log("Date:\t" + commit.date());

        // Give some space and show the message.
        console.log("\n    " + commit.message());
        commit.getDiff().then(arrayDiff => {
            console.log("\n======= arrayDiff.length() = ", arrayDiff.length);
            for (let diff of arrayDiff) {
                console.log("\n======= diff = ", diff.numDeltas());
                if (diff.numDeltas() > 0) {
                    let d = diff.getDelta(0);
                    console.log(`
flags       : ${d.flags()},
newFile     : ${d.newFile().path()},
nfiles      : ${d.nfiles()}
oldFile     : ${d.oldFile().path()}
similarity  : ${d.similarity()}
status      : ${d.status()}
newFile     : ${d.newFile()}
`);
                }

                diff.patches().then(function (patches) {
                    patches.forEach(function (patch) {
                        patch.hunks().then(function (hunks) {
                            hunks.forEach(function (hunk) {
                                hunk.lines().then(function (lines) {
                                    console.log("diff", patch.oldFile().path(), patch.newFile().path());
                                    console.log(hunk.header().trim());
                                    lines.forEach(function (line) {
                                        console.log(String.fromCharCode(line.origin()) +
                                            line.content().trim());
                                    });
                                });
                            });
                        });
                    });
                });
            }
        });

        console.log();
    });
    history.on("end", function (commits) {
        console.log("-----" + count + "  " + commits.length);
        console.log(c);
    });

    // Start emitting events.
    history.start();
});


//G.Repository.open(gitRepoUrl).then(function (repo) {
//    console.log("111" + repo);
//    repo.getReferences(G.Reference.TYPE.LISTALL).then(function (refs) {
//
//        if (!ref.isBranch()) {
//            return;
//        }
//
//        let revWalk = G.Revwalk.create(repo);
//
//
//    });
//});


//
//
///*
// projectName, user, commitCount, lineAdded,lineDeleted,lineTotal, unrebasedCommitCount, unMergedCommitCount, unrebasedCommits, unMergedCommits
//*/
//ls.stdout.on('data', (data) => {
//    console.log(`stdout: ${data}`);
//});
//
//ls.stderr.on('data', (data) => {
//    console.log(`stderr: ${data}`);
//});
//
//ls.on('close', (code) => {
//    console.log(`child process exited with code ${code}`);
//});
