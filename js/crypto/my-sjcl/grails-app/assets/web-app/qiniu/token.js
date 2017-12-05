// node http://developer.qiniu.com/code/v6/sdk/nodejs.html
var qiniu = require("qiniu");
qiniu.conf.ACCESS_KEY = 'aLIjaY2adjM5HC5sDCPVLJYam74lLKgJ1HM-oehT';
qiniu.conf.SECRET_KEY = 'In0fWPyrtAoXplKHpO1GA_KMzFmEW2jypHTBtWR8';

// http://developer.qiniu.com/code/v6/sdk/nodejs.html#io-put

var bucket = 'test';
var key = "Fnep7z2EENvNTVohEB5L05tVeRhw";

var scope = bucket + ":" + key;
// var deadline =new Date().getTime()+ 3600;


var putPolicy = new qiniu.rs.PutPolicy2({scope: scope});
//console.log(process.argv);
//console.log(process.env.HOME);
console.log(putPolicy.token());
// putPolicy.expires = 3600;