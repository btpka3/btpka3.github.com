/**
 * 测试读写json文件
 */

var fs = require('fs');
var obj = JSON.parse(fs.readFileSync('test0.json', 'utf8'));
obj.c = new Date();
console.log(obj);
fs.writeFile('test1.json', JSON.stringify(obj, null, 2), function (err) {
    if (err) {
        return console.log(err);
    }
    console.log('add c property');
});
