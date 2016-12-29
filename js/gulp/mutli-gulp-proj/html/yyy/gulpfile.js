const gulp = require('gulp');
const chalk = require('chalk');
const gutil = require('gulp-util');

gulp.task("hi", cb => {
    gutil.log(`-----------${chalk.red(__filename)}, hi   , a=${process.env.a}`);
});
gulp.task("hello", cb => {
    gutil.log(`-----------${chalk.red(__filename)}, hello, a=${process.env.a}`);
});


// 直接 `node gulfile.js hi hello`
// function isInvokedDirectly() {
//     if (process.argv.length < 2) {
//         return false;
//     }
//     if (process.argv[1] === __filename) {
//         return true;
//     }
//     if (process.argv[1] === __filename.replace(/\.js$/, '')) {
//         return true;
//     }
//     return false;
// }
//
// if (isInvokedDirectly()) {
//     gulp.start(process.argv.slice(2));
// }

 