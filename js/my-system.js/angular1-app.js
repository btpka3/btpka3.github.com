import angular from "angular";
import "angular-material";
import "bootstrap";
import "bootstrap/css/bootstrap.css!"
import "./angular1.css!"  // FIMXE: 为何通过 meta:{'*.css': {loader: 'css'}} 仍然不起作用？
import IndexController from "./angular1-IndexController.js";


var app = angular.module("app", ['ngMaterial']);
app.controller("IndexController", IndexController);
console.log("-------1112");
export default  app;