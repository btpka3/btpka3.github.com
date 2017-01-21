import angular from "angular";
import appConfigFactory from "./appConfigFactory.js";

/**
 * 该模块根据打包时的命令行参数提供不同的 配置数据。
 */
export default angular.module('app.conf', [])
    .factory(appConfigFactory);
