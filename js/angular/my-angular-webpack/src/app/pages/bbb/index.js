import angular from "angular";
import uiRouter from "angular-ui-router";
import confState from "./confState.js";

export default angular.module('main.bbb', [
    uiRouter
])
    .config(confState);

