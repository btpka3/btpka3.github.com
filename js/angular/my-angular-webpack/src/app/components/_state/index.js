import angular from "angular";
import uiRouter from "angular-ui-router";
import ocLazyload from "oclazyload";
import confFutureState from "./confFutureState.js";
import "ui-router-extras";


console.log("-000000999 ----", ocLazyload,)
/**
 * 该模块用来注册所有的 futureState
 */
export default angular.module('app.state', [
    //'oc.lazyLoad',
    uiRouter,
    ocLazyload,
    "ct.ui.router.extras"
])
    .config(confFutureState);
