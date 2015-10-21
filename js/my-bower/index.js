#!/usr/bin/env node
'use strict';

var bower = require('bower');

console.log("~~~~~~~~" + bower.version);

var logger = bower.commands["info"]("jquery#2.1.4");

logger
    .on('end', function (data) {
        console.log("end~~~~~~~~" + JSON.stringify(data));
    })
    .on('error', function (err)  {
        console.log("error~~~~~~~~" + err);
    })
    .on('log', function (log) {
        console.log("log~~~~~~~~" + JSON.stringify(log));
    })
    .on('prompt', function (prompt, callback) {
        console.log("prompt~~~~~~~~" + prompt);
    });