const gulp = require('gulp');
const sass = require('gulp-sass');


// ------------------------------------------------------------------------------ task : app.scss
const appScssSrc = 'src/app.scss';
const appScssWatchSrc = appScssSrc;
gulp.task('app.scss', cb => {

    gulp.src(appScssSrc)

        .pipe(sass({
            errLogToConsole: true,
            //outputStyle: "compressed"
        }))

        .pipe(gulp.dest("src"))
        .on('finish', () => {
            cb();
        });

});


// // ------------------------------------------------------------------------------ task : default / watch
//
// gulp.task('default', ['app.scss'], cb => { //,
//
//     gulp.watch(appScssWatchSrc, ['app.scss']);
//
// });

 