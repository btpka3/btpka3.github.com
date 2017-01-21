/**
 * 定义所有的状态。
 *
 * 1. 会以状态名来生成独立的 bundle。
 * 2. 会生成用以远程下载的 futureState 的 json 数据。
 */
module.exports = [
    // [ name, url, src, type?]
    ["main", ""],
    ["main.aaa", "/"],
    ["main.bbb", "/bbb"],
    ["main.ccc", "/ccc"]
];
