'use strict';

// 一个小题目：有一个 字符串数组，要求遍历：每隔1秒，打印其中下一个元素。
var messages = ["aaa", "bbb", "ccc"];

function s(arr, i) {
    if (i >= arr.length) {
        return;
    }
    setTimeout(function () {
        console.log(new Date(), arr[i]);
        i++;
        s(arr, i);
    }, 1000);
}

s(messages, 0);

