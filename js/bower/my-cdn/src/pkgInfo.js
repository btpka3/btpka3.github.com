#!/usr/bin/env node

/**
 * 测试如何通过bower获取package信息
 */
'use strict';

var Q = require('q');
var bower = require('bower');
var Logger = require('bower-logger');

//console.log("====== " + (Q.fcall(function () {
//        return 1;
//    }) instanceof Q.makePromise));
//Q.fcall(Q.all([function () {
//    return 1;
//}]))
//    .spread(function (value) {
//        console.log("================" + value, value)
//    });

//Q.all([]);

//console.log("bower = ", bower);
console.log("~~~~~~~~~~~~ = ", bower.commands.info);
console.log("~~~~~~~~~~~~ = ", "abc".match(/b/));
//var logger = bower.commands.info("jquery#2.1.4");
var logger = bower.commands.install(["jquery#2.1.4"]);

logger
    .on('end', function (data) {
        console.log("end : ", data);
    })
    .on('error', function (err) {
        console.log("error : ", err);
    });


/*
 Promise
 */
