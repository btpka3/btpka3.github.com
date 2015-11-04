var gulp = require('gulp');
var gulpLoadPlugins = require('gulp-load-plugins');
var plugins = gulpLoadPlugins();
//var jshint = require('gulp-jshint');
//var uglify = require('gulp-uglify');
//var concat = require('gulp-concat');
var beautify = require('gulp-beautify');

gulp.task('default', function () {
    return gulp.src('templates/**/*.html')
        .pipe(templateCache())
        .pipe(gulp.dest('target'));
});

gulp.task('minify', function () {
    gulp.src('src/index.js')
        .pipe(plugins.uglify())
        .pipe(plugins.concat('app.js'))
        .pipe(plugins.rev())
        .pipe(gulp.dest('target'))
        .pipe(plugins.rev.manifest({
            base: 'build/assets',
            merge: true // merge with the existing manifest (if one exists)
        }))
        .pipe(gulp.dest('target'))

});



gulp.task('b', function () {
    console.log("==== 111 = ");
    var argv = require('yargs').argv;
    console.log("==== env = "+argv.env);
});

gulp.task('w', function () {
    gulp.watch('src/*', ['b']);
});

