import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState.js";
//import appRun from "./run";
import "./css.scss"

console.log("-000000 ----", uiRouter);

export default angular.module('main', [uiRouter])
    .config(confState)
// .run(appRun);





