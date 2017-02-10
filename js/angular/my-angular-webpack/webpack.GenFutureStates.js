const process = require('process');
const states = require("./webpack.states");

const fs = require('fs');
const glob = require('glob');
const path = require('path');
const assert = require('assert');
const NodeTemplatePlugin = require('webpack/lib/node/NodeTemplatePlugin');
const NodeTargetPlugin = require('webpack/lib/node/NodeTargetPlugin');
const LoaderTargetPlugin = require('webpack/lib/LoaderTargetPlugin');
const LibraryTemplatePlugin = require('webpack/lib/LibraryTemplatePlugin');
const SingleEntryPlugin = require('webpack/lib/SingleEntryPlugin');
const getStateEntries = require("./webpack.getStateEntries");

var args = require('yargs').argv;
var isProd = args.env && args.env.prod;

/**
 * 生成预定义好的futureStates的数组。
 * 示例如下：
 * [
 *   // [状态名称， 状态URL，状态打包文件]
 *   ["main",     "",     "main.90ba1d50c154614dd6d9.js",       ["main.xxxxxx.css"]],
 *   ["main.aaa", "/",    "main.aaa.cdbee811327d854d9e24.js",   ["main.aaa.xxxxxx.css"]],
 *   ["main.bbb", "/bbb", "main.bbb.309e31daab8f6c412668.js",   ["main.bbb.xxxxxx.css"]],
 *   ["main.ccc", "/ccc", "main.ccc.b31716d65ab745c9f590.js",   ["main.ccc.xxxxxx.css"]]
 * ]
 */

// 为了后续查找方便，先转换为对象。
const statesObj = states.reduce((preVal, curVal, curIdx) => {
    preVal[curVal[0]] = curVal;
    return preVal
}, {});

class GenFutureStates {

    /**
     * 构造函数，当前需要一个 选项 "htmlDir", 以便生成相对于该html的额外资源的URL路径。
     *
     * @param options
     */
    constructor(options) {

        // FIXME 如果使用该方式，可能 htmlWebpackPlugin 尚未初始化？且在webpack配置中要有相应的先后顺序？
        if (options && options.htmlWebpackPlugin) {
            this.htmlWebpackPlugin = options.htmlWebpackPlugin
        }
        if (options && options.htmlDir) {
            this.htmlDir = options.htmlDir
        }
    }

    // 构建额外的资源
    buildExtraAssets(compiler, compilation) {

        var self = this;
        let tmpStates = {};
        let stateJsonFile = "./.myStates.js";

        // webpack 配置的输出目录（绝对路径）
        let outputPath = compilation.getPath(compiler.outputPath);
        // html-webpack-plugin 要生成的 html的相对路径
        let htmlDir = path.resolve(outputPath, self.htmlDir);

        // 遍历已经生成，但尚未存盘的 chunk，判断其是否是要保留的 ui-router 的状态js。
        compilation.chunks.forEach(function (chunk) {

            // 判断当前 chunk 是否是一个 ui-router 状态
            let curState = statesObj[chunk.name];
            if (!curState) {
                return;
            }
            let stateName = curState[0];


            let jsFile = chunk.files.find((file) => {
                return file.startsWith(stateName)
            });
            assert(jsFile, `状态 [${stateName}] 没有找到相应主入口 js 文件。候选有：${chunk.files}`);

            // 已生成js文件的绝对路径
            let jsPath = path.resolve(outputPath, jsFile);

            // 已生成js文件的绝对路径
            let jsFileUrl = "./" + path.relative(
                    htmlDir,
                    jsPath
                ).split(path.sep).join('/'); // URL，防止windows操作系统下生成的路径使用 '\'

            // let cssArr = chunk.files
            //     .filter(f => f.endsWith(".css"))
            //     .map(f => {
            //         return "./" + path.relative(htmlDir, path.resolve(outputPath, f))
            //                 .split(path.sep)
            //                 .join('/')
            //     });
            //console.log("------btpka3: " + chunk.name + "   " + chunk.files + "; cssArr = " + cssArr);
            //console.log(`--------======= GenFutureStates: jsFileUrl =${jsFileUrl}`);
            tmpStates[stateName] = [
                curState[0],    // ui-router 中 匹配的 状态名称
                curState[1],    // ui-router 中 匹配的 url
                jsFileUrl,      // 相对于 index.html 要加载的 JS 文件的路径
                //cssArr
            ];
        });

        // Object => Array : 相当于  Object.values(tmpStates)
        let stateArr = Object.keys(tmpStates).map(key => {
            return tmpStates[key]
        });

        let jsContent = `/* comment test btpka3. */ module.exports = ${JSON.stringify(stateArr)};`;
        fs.writeFileSync(stateJsonFile, jsContent);  // FIXME 目录不存在
    }


    genChildCompiler(compilation) {
        // 追加额外的资源
        // http://stackoverflow.com/questions/28987626/using-a-loader-inside-a-webpack-plugin/30344170#30344170
        var outputOptions = {
            //filename: "ttt.js",
            filename: isProd ? '[name].[hash].js' : '[name].js',
            publicPath: compilation.outputOptions.publicPath
        };
        var compilerName = "myStates";
        var entryName = "myStates";

        var childCompiler = compilation.createChildCompiler(compilerName, outputOptions);
        //childCompiler.outputFileSystem = compiler.outputFileSystem;
        childCompiler.apply(
            new NodeTemplatePlugin(outputOptions),
            new NodeTargetPlugin(),
            new LibraryTemplatePlugin('result', 'var'),
            new SingleEntryPlugin(this.context, "expose-loader?myStates!./.myStates.js", entryName),
            new LoaderTargetPlugin('node')
        );
        childCompiler.plugin('compilation', function (compilation) {
            if (compilation.cache) {
                if (!compilation.cache[compilerName]) {
                    compilation.cache[compilerName] = {};
                }
                compilation.cache = compilation.cache[compilerName];
            }
        });

        return childCompiler;
    }
}

GenFutureStates.prototype.apply = function (compiler) {
    const self = this;


    var extraAssetCompilerPromise;

    // 获取 compilation 实例
    compiler.plugin('compilation', function (compilation, params) {

        // webpack 配置的输出目录（绝对路径）
        let outputPath = compilation.getPath(compiler.outputPath);
        // html-webpack-plugin 要生成的 html的相对路径
        let htmlDir = path.resolve(outputPath, self.htmlDir);


        // 注册 html-webpack-plugin 事件处理，等待生成额外的资源，并注入
        compilation.plugin('html-webpack-plugin-before-html-processing', function (htmlPluginData, callback) {

            // 若有多个本plugin实例，或多个 HtmlWebpackPlugin 实例时，过滤目标处理对象。
            if (self.htmlWebpackPlugin && self.htmlWebpackPlugin !== htmlPluginData.plugin) {
                callback();
                return;
            }

            // htmlPluginData = {html, assets, plugin, outputName};
            extraAssetCompilerPromise.then(function (extraAssets) {
                let outputPath = compilation.getPath(compiler.outputPath);

                let extraAssetFiles = extraAssets.map(extraAssetName => {
                    var extraAssetPath = path.resolve(outputPath, extraAssetName);
                    //var htmlPath = path.resolve(outputPath, htmlPluginData.plugin.options.filename);

                    return "./" + path.relative(htmlDir, extraAssetPath);
                });

                // 追加到最前面。
                Array.prototype.unshift.apply(htmlPluginData.assets.js, extraAssetFiles);

                callback(null, htmlPluginData);
            }, function (err) {
                callback(err);
            });
        });
    });

    // 所需的资源都编译好，准备发布时, 生成额外的资源
    compiler.plugin('emit', function (compilation, callback) {

        extraAssetCompilerPromise = new Promise(function (resolve, reject) {
            try {
                // 先生成额外的资源
                self.buildExtraAssets(compiler, compilation);

                // 再构建(压缩、hash）
                let childCompiler = self.genChildCompiler(compilation);

                childCompiler.runAsChild(function (err, entries, childCompilation) {
                    if (err) {
                        reject(err);
                    }

                    // var extraAssets = [];
                    // childCompilation.chunks.forEach(function (chunk) {
                    //     extraAssets.push(chunk.files[0]);
                    // });

                    var extraAssets = childCompilation.chunks.map(chunk => chunk.files[0]);
                    resolve(extraAssets);
                    callback();
                });
            } catch (err) {
                reject(err);
                callback(err);
            }
        });

    });
};


module.exports = exports = GenFutureStates;
