'use strict';

// 一个小题目：有一个 字符串数组，要求遍历：每隔1秒，打印其中下一个元素。


function aaa(arr) {
    var messages = arr;
    var i = 0;
    var handler = null;

    function iii() {
        console.log(new Date(), messages[i]);
        i++;
        if (i >= messages.length) {
            clearInterval(handler);
        }
    }
    handler = setInterval(iii, 1000);
}

aaa(["aaa", "bbb", "ccc"]);

