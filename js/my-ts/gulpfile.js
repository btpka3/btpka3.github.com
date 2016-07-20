var gulp = require("gulp");
var ts = require("gulp-typescript");
//var tsProject = ts.createProject("tsconfig.json");

gulp.task("default", function () {
    return gulp.src(['src/**/*.ts', 'src/**/*.tsx'])
        .pipe(ts({
            noImplicitAny: true,
            "target": "es5",
            //out: 'output.js'
        }))
        .pipe(gulp.dest('dist'));
});