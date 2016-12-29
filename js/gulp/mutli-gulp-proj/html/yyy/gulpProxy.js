const gulp = require('gulp');

require("./gulpfile");

if (process.argv.length >= 2) {
    gulp.start(process.argv.slice(2));
} else {
    gulp.start();
}
