/**
 * 该目录文件是通过 node.js + express.js 来模拟后台 JSON API。
 * 请使用命令 node mock/index.js 来启动
 */

var express = require('express');
var app = express();

var server = app.listen(3000, function () {
    console.log('Listening on port %d', server.address().port);
});

////////////////////////////////////////////// views
app.set('views', __dirname);
app.set('view engine', 'jade');

////////////////////////////////////////////// route

app.use('/', express.static('./'));
//app.use('/', express.static('src'));

app.use(require("./data"));


app.use(function (err, req, res, next) {
    console.error(err.stack);
    res.status(500).send('Something broke!');
});

app.use(function (req, res, next) {
    res.status(404).send('Sorry cant find that!');
});
