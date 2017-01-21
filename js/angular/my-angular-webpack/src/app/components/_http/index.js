import angular from "angular";
import confHttp from "./confHttp.js";
import confNormalizeHttpResp from "./confNormalizeHttpResp.js";

/**
 * 该模块用来预处理 http 请求与响应
 *
 * 参考： https://docs.angularjs.org/api/ng/service/$http#interceptors
 */
export default angular.module('app.http', [])
    .config(confNormalizeHttpResp)
    .config(confHttp);
