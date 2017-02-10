import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState.js";

export default angular.module('main.aaa.a1', [
    uiRouter
])
    .config(confState);

