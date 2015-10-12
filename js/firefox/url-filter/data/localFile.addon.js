"use strict";
// https://developer.mozilla.org/en-US/docs/Mozilla/JavaScript_code_modules/FileUtils.jsm
const {Cc,Ci, Cu,components} = require("chrome");

Cu.import("resource://gre/modules/FileUtils.jsm");
Cu.import("resource://gre/modules/NetUtil.jsm");

// ----------------------------------------------------------- List dirctory

var dir = "/";
var file = new FileUtils.File(dir);

//var file = Cc["@mozilla.org/file/local;1"]
//    .createInstance(Ci.nsILocalFile);
//file.initWithPath(dir);

console.log("is directory : " + file.isDirectory());
// nsISimpleEnumerator
var entries = file.directoryEntries;

while (entries.hasMoreElements()) {
    var f = entries.getNext().QueryInterface(Ci.nsIFile);
    console.log("" + dir + f.leafName);
}


// ----------------------------------------------------------- write file

var fileName = '/tmp/btpka3.txt';
// nsILocalFile
file = new FileUtils.File(fileName);

if (file.exists() == false) {
    console.log("create file : " + fileName);
    file.create(Ci.nsIFile.NORMAL_FILE_TYPE, parseInt('0777', 8));
}


var foStream = Cc["@mozilla.org/network/file-output-stream;1"]
    .createInstance(Ci.nsIFileOutputStream);
// 0x02     PR_WRONLY
// 0x08     PR_CREATE_FILE
// 0x20     PR_TRUNCATE
foStream.init(file, 0x02 | 0x08 | 0x20, parseInt('0777', 8), 0);

var converter = Cc["@mozilla.org/intl/converter-output-stream;1"]
    .createInstance(Ci.nsIConverterOutputStream);
converter.init(foStream, "UTF-8", 0, 0);
var data = 'Hello 世界 ~ ' + new Date();
converter.writeString(data);
converter.close();
console.log("write to file : " + fileName + " : " + data);

// ----------------------------------------------------------- read file
NetUtil.asyncFetch(file, function (inputStream, status) {
    console.log("read from file : " + status);
    if (!components.isSuccessCode(status)) {
        // Handle error!
        return;
    }

    // The file data is contained within inputStream.
    // You can read it into a string with
    var data = NetUtil.readInputStreamToString(inputStream, inputStream.available(), {charset: "UTF-8"});
    console.log("read from file : " + fileName + " : " + data);
});