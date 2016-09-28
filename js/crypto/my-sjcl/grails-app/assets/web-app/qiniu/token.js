// node http://developer.qiniu.com/code/v6/sdk/nodejs.html
var qiniu = require("qiniu");
qiniu.conf.ACCESS_KEY = 'xxx';
qiniu.conf.SECRET_KEY = 'yyy';

// http://developer.qiniu.com/code/v6/sdk/nodejs.html#io-put

var bucket = 'test';
var key = "c219f8dad901957a01d9b9b6baeac042823d9665";

var scope = bucket + ":" + key;
// var deadline =new Date().getTime()+ 3600;


var putPolicy = new qiniu.rs.PutPolicy2({scope: scope});
//console.log(process.argv);
//console.log(process.env.HOME);
console.log(putPolicy.token());
// putPolicy.expires = 3600;