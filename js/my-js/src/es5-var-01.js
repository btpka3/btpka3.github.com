'use strict';

var messages = ["aaa", "bbb", "ccc"];
for (var i = 0; i < messages.length; i++) {
    setTimeout(function () {
        console.log(i + " : " + messages[i]);
    }, i * 1500);
}
console.log(i + " : " + messages[i]);
