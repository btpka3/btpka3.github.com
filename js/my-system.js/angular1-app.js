import angular from "angular";
import "angular-material";
import IndexController from "./angular1-IndexController.js";


var app = angular.module("app", ['ngMaterial']);
app.controller("IndexController", IndexController);
console.log("-------1111");
export default  app;