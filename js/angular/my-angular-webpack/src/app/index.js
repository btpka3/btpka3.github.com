import angular from "angular";
import uiRouter from "angular-ui-router";
import "angular-material/angular-material.css";
import appConf from "./components/_conf";
import appHttp from "./components/_http";
import appCommon from "./components/_common"
import appState from "./components/_state"
import runState from "./runState";
import confState from "./confState.js";
import d3 from "d3";
import "ui-router-extras";


console.log("global === window", global === window, global.d3, d3);
export default angular.module('app', [
    uiRouter,
    appHttp.name,
    appConf.name,
    appState.name,
    appCommon.name,
    "ct.ui.router.extras"
])
    // .config(confState)
    .run(runState)
    .run(function () {
        console.log("00099999999999-------------------zll12")
    })

// ff
//import offlinePlugin from  'offline-plugin/runtime'
//offlinePlugin.install();
