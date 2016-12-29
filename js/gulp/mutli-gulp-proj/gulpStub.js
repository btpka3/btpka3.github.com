const gulp = require('gulp');

if (process.argv.length >= 3) {
    require(process.argv[2]);
    gulp.start(process.argv.slice(3));
}
