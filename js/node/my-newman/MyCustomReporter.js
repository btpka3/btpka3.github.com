var _ = require('lodash');


// events: https://github.com/postmanlabs/newman#newmanrunevents

/**
 * Reporter that simply dumps the summary object to file (default: newman-run-report.json).
 *
 * @param {Object} newman - The collection run object, with event hooks for reporting run details.
 * @param {Object} options - A set of collection run options.
 * @param {String} options.export - The path to which the summary object must be written.
 * @returns {*}
 */
module.exports = function (newman, options) {
    newman.on('beforeDone', function (err, o) {
        if (err) {
            return;
        }

        // newman.exports.push({
        //     name: 'json-reporter',
        //     default: 'newman-run-report.json',
        //     path: options.export,
        //     content: JSON.stringify(_.omit(o.summary, 'exports'), 0, 2)
        // });
    });
};
//
//
// /**
//  * Reporter that simply dumps the summary object to file (default: newman-run-report.json).
//  *
//  * @param {Object} eventEmitter - EventEmitter
//  * @param {Object} options - A set of collection run options.
//  * @param {String} options.export - The path to which the summary object must be written.
//  * @returns {*}
//  * @see <a href="https://nodejs.org/docs/latest/api/events.html#class-eventemitter">EventEmitter</a>
//  */
// function myReporter(eventEmitter, reporterOptions, collectionRunOptions) {
//     // emitter is an event emitter that triggers the following events: https://github.com/postmanlabs/newman#newmanrunevents
//     // reporterOptions is an object of the reporter specific options. See usage examples below for more details.
//     // collectionRunOptions is an object of all the collection run options: https://github.com/postmanlabs/newman#newmanrunoptions-object--callback-function--run-eventemitter
//
//     newman.on('beforeDone', function (err, o) {
//         if (err) {
//             return;
//         }
//
//         newman.exports.push({
//             name: 'json-reporter',
//             default: 'newman-run-report.json',
//             path: options.export,
//             content: JSON.stringify(_.omit(o.summary, 'exports'), 0, 2)
//         });
//     });
//
// }
//
// module.exports = myReporter