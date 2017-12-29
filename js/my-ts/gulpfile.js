var gulp = require("gulp");
var ts = require("gulp-typescript");
var babel = require("gulp-babel");

//var tsProject = ts.createProject("tsconfig.json");

gulp.task("default", function () {
    return gulp.src(['src/**/*.ts', 'src/**/*.tsx'])
        .pipe(ts({
            noImplicitAny: true,
            //"target": "es6",
            "target": "es2015",
            "module": "commonjs",
            "moduleResolution": "node",
            //out: 'output.js'
        }))
        .pipe(gulp.dest('dist/ts'))
        .pipe(babel())
        .pipe(gulp.dest("dist/babel"));
        ;
});