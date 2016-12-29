const gulp = require('gulp');
// const exec = require('child_process').exec;
// const spawn = require('child_process').spawn;
const fork = require('child_process').fork;
const fs = require('fs');
const glob = require('glob');
const gutil = require('gulp-util');

gulp.task("default", cb => {
    glob("html/*/", function (er, files) {
        files.forEach((projDirName)=> {

            // TODO 判断  gulpfile.js 是否存在

            // let startScript = "gulpfile";
            let startScript = "gulpProxy";

            let projExe = fork(startScript, ['hi', 'hello'], {
                cwd: projDirName,
                stdio: 'inherit',
                env: process.env
            });

            projExe.on('close', (code) => {
                gutil.log("=======close : " + code);
            });

            projExe.on('error', (a) => {
                gutil.log("=======error : " + a);
            });
        });
    });

});

