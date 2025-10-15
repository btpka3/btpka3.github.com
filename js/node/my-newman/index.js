const newman = require('newman');
const {Buffer} = require('node:buffer');
const fs = require('fs');
const _ = require("lodash");
const MyCustomReporter = require("./MyCustomReporter");

let jsonStr2 = fs.readFileSync(".tmp/options.json");
let opts = JSON.parse(jsonStr2);
console.log("opts", opts);


// call newman.run to pass `options` object and wait for callback
let emitter = newman.run(opts, function (err, summary) {
    if (err) {
        throw err;
    }

    let respStreamBuf = summary.run.executions[0].response.stream;
    let respBodyStr = respStreamBuf.toString();

    console.log('collection run complete!', summary);
});

// emitter.on('beforeScript', function (err, o) {
//     console.log(err, o);
// });
//
// emitter.on('item', function (err, o) {
//     console.log(err, o);
// });

// emitter.on('beforeDone', function (err, o) {
//     // bubble special script name based events
//
//     let MyCustomReporter = require('./MyCustomReporter');
//     emitter.reporters["MyCustomReporter"] = new MyCustomReporter(emitter);
//     console.log(err,o);
// });

// emitter.on('start', function (err, o) {
//     // bubble special script name based events
//
//     let MyCustomReporter = require('./MyCustomReporter');
//     emitter.reporters["MyCustomReporter"] = new MyCustomReporter(emitter);
//     console.log(err,o);
// });
//emitter.reporters["MyCustomReporter"] = require('./MyCustomReporter');