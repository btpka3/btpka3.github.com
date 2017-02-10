/**
 * 定义所有的状态。
 *
 * 1. 会以状态名来生成独立的 bundle。
 * 2. 会生成用以远程下载的 futureState 的 json 数据。
 * 3. 因为url路径无法通过程序判定，故，仍需手动编写该文件。
 */
module.exports = [
    // [ name, url, src, type?]
    ["main", ""],
    ["main.aaa", "/"],
    ["main.aaa.a1", "/a1"],
    ["main.aaa.a2", "/a2"],
    ["main.bbb", "/bbb"],
    ["main.ccc", "/ccc"]
];
