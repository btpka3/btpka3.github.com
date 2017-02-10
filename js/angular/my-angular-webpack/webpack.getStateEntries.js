const fs = require('fs');
const glob = require('glob');
const path = require('path');

/**
 * 通过根据一定规则自动生成 future state 列表。
 */
function dirToState(dir) {
    let state = dir                         // => "src/app/pages/aaa/a1/"
            .replace("src/app/", "")        // => "pages/aaa/a1/"
            .replace(/\/$/, "")             // => "pages/aaa/a1"
            .replace(/^pages/, "main")      // => "main/aaa/a1"
            .split(path.sep).join(".")      // => "main.aaa.a1"
        ;
    return state;
}


function dirToEntryPath(dir) {
    let entryPath = dir                     // => "src/app/pages/aaa/"
            .replace(/^/, "./")             // => "./src/app/pages/aaa/"
            .replace(/\/$/, "")             // => "./src/app/pages/aaa"
        ;
    return entryPath;
}

function getStateEntries() {
    let stateEntry = {};

    glob.sync("src/app/pages/**/").forEach((dir) => {

        // 检查目录是否存在 index.js
        try {
            // 存在，但不是文件
            if (!fs.statSync(path.resolve(dir, "index.js")).isFile()) {
                return;
            }
        } catch (err) {
            // 不存在 index.js
            return;
        }

        let state = dirToState(dir);
        let entryPath = dirToEntryPath(dir);

        stateEntry[state] = [entryPath];
    });
    //console.log(stateEntry);
    return stateEntry
}
module.exports = exports = getStateEntries;
