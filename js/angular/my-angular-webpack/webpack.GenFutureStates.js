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

/**
 * 生成预定义好的futureStates的数组。
 * 示例如下：
 * [
 *   // [状态名称， 状态URL，状态打包文件]
 *   ["main",     "",     "main.90ba1d50c154614dd6d9.js"],
 *   ["main.aaa", "/",    "main.aaa.cdbee811327d854d9e24.js"],
 *   ["main.bbb", "/bbb", "main.bbb.309e31daab8f6c412668.js"],
 *   ["main.ccc", "/ccc", "main.ccc.b31716d65ab745c9f590.js"]
 * ]
 */

// 为了后续查找方便，先转换为对象。
//const states = getStateEntries();
console.log("--==--==::", states);
const statesObj = states.reduce((preVal, curVal, curIdx) => {
    preVal[curVal[0]] = curVal;
    return preVal
}, {});

class GenFutureStates {
    constructor(options) {

        if (options && options.htmlWebpackPlugin) {
            this.htmlWebpackPlugin = options.htmlWebpackPlugin
        }
        if (options && options.htmlDir) {
            this.htmlDir = options.htmlDir
        }
        console.log('--------new GenFutureStates  : htmlDir = ' + this.htmlDir);

    }

    findoutPath(compilation) {
        console.log("------------ compilation.options.output.path :" + compilation.options.output.path)
        console.log("------------ compilation.options.output.publicPath :" + compilation.options.output.publicPath)
        var self = this;
        var webpackStatsJson = compilation.getStats().toJson();

        // Use the configured public path or build a relative path
        var publicPath = typeof compilation.options.output.publicPath !== 'undefined'
            // If a hard coded public path exists use it
            ? compilation.mainTemplate.getPublicPath({hash: webpackStatsJson.hash})
            // If no public path was set get a relative url path
            : path.relative(compilation.options.output.path, compilation.options.output.path)
                .split(path.sep).join('/');

        if (publicPath.length && publicPath.substr(-1, 1) !== '/') {
            publicPath += '/';
        }
        return publicPath
    }

    // 构建额外的资源
    buildExtraAssets(compiler, compilation) {

        var self = this;
        console.log("-----------------ccccccccc this = ", this);
        let tmpStates = {};

        compilation.chunks.forEach(function (chunk) {
            console.log(`--------======= GenFutureStates: chunk :
                id=${chunk.id}, 
                name=${chunk.name},
                files=${chunk.files}`);

            // 判断当前 chunk 是否是一个 ui-router 状态
            let curState = statesObj[chunk.name];
            if (!curState) {
                return;
            }
            let stateName = curState[0];

            let jsFile = chunk.files.find((file) => {
                console.log(`--------======= GenFutureStates: file =${file}, typeof = ${typeof file}`);
                return file.startsWith(stateName)
            });
            assert(jsFile, `状态 [${stateName}] 没有找到相应主入口 js 文件。候选有：${chunk.files}`);

            console.log("-----------------dddddddddd this = ", self);
            let outputPath = compilation.getPath(compiler.outputPath);
            var htmlDir = path.resolve(outputPath, self.htmlDir);
            var jsPath = path.resolve(outputPath, jsFile);
            let jsFileUrl = "./" + path.relative(
                    htmlDir,
                    jsPath
                ).split(path.sep).join('/'); // URL

            console.log(`--------======= GenFutureStates: jsFileUrl =${jsFileUrl}`);
            tmpStates[stateName] = [
                curState[0],    // ui-router 中 匹配的 状态名称
                curState[1],    // ui-router 中 匹配的 url
                jsFileUrl,      // 相对于 index.html 要加载的 JS 文件的路径
            ];
        });
        //fs.writeFileSync("./src/myplugin.js", "var mypluginData=" + JSON.stringify(arr) + ";");

        //let stateJsonFile = path.resolve(compilation.options.output.path, "states.json");
        let stateJsonFile = "./.myStates.js";
        console.log(`--------======= GenFutureStates: stateJsonFile =${stateJsonFile}`);

        //console.log(`--------======= GenFutureStates: outputPath =${outputPath}`);
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
            filename: '[name].[hash].js',
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


// 1111
GenFutureStates.prototype.apply = function (compiler) {
    const self = this;

    var aaa;
    console.log(`--------======= GenFutureStates: compiler.outputPath = ${compiler.outputPath}`);

    // 获取 compilation 实例
    compiler.plugin('compilation', function (compilation, params) {

        // 注册 html-webpack-plugin 事件处理，等待生成额外的资源，并注入
        compilation.plugin('html-webpack-plugin-before-html-processing', function (htmlPluginData, callback) {

            // 若有多个本plugin实例，或多个 HtmlWebpackPlugin 实例时，过滤目标处理对象。
            if (self.htmlWebpackPlugin && self.htmlWebpackPlugin !== htmlPluginData.plugin) {
                callback();
                return;
            }

            //htmlPluginData.html += 'The magic footer';

            console.log("--------------------self.htmlWebpackPlugin = htmlPluginData.plugin :::::::: ",
                (self.htmlWebpackPlugin === htmlPluginData.plugin)
            );
            console.log("--------------------htmlPluginData = ", htmlPluginData);

            // {html, assets, plugin, outputName};
            console.log("--------------------htmlWebpackPlugin.assets =  ", htmlPluginData.assets);
            console.log("--------------------aaa =  ", aaa);

            aaa.then(
                function (extraAssets) {

                    let outputPath = compilation.getPath(compiler.outputPath);

                    let extraAssetFiles = extraAssets.map(extraAssetName => {

                        var extraAssetPath = path.resolve(outputPath, extraAssetName);
                        var htmlPath = path.resolve(outputPath, htmlPluginData.plugin.options.filename);
                        console.log("--------------------outputPath =  ", outputPath);
                        console.log("--------------------extraAssetPath =  ", extraAssetPath);
                        console.log("--------------------htmlPath =  ", htmlPath);

                        // var publicPath = typeof compilation.options.output.publicPath !== 'undefined'
                        //     // If a hard coded public path exists use it
                        //     ? compilation.mainTemplate.getPublicPath({hash: webpackStatsJson.hash})
                        //     // If no public path was set get a relative url path
                        //     : path.relative(compilation.options.output.path, compilation.options.output.path)
                        //         .split(path.sep).join('/');

                        return "./" + path.relative(path.dirname(htmlPath), extraAssetPath);

                    });


                    console.log("--------------------extraAssets =  ", extraAssets, extraAssetFiles);
                    // 追加到最前面。
                    Array.prototype.unshift.apply(htmlPluginData.assets.js, extraAssetFiles);
                    console.log("--------------------htmlPluginData.assets.js =  ", htmlPluginData.assets.js);
                    callback(null, htmlPluginData);
                },
                function (err) {
                    console.log("--------err", err);
                    callback(err);
                }
            );
            // callback(null, htmlPluginData);
        });
    });

    // 所需的资源都编译好，准备发布时, 生成额外的资源
    compiler.plugin('emit', function (compilation, callback1) {

        aaa = new Promise(function (resolve, reject) {

            // 先生成额外的资源
            self.buildExtraAssets(compiler, compilation);

            // 再构建(压缩、hash）
            let childCompiler = self.genChildCompiler(compilation);
            childCompiler.runAsChild(function (err, entries, childCompilation) {

                console.log("------childCompiler-err", err);

                var arr = [];
                childCompilation.chunks.forEach(function (chunk) {
                    console.log(`--------=======11 childCompilation: chunk :
                        id=${chunk.id},
                        name=${chunk.name},
                        files=${chunk.files}`);

                    arr.push(chunk.files[0])
                });
                callback1();
                resolve(arr)
            });
        });

    });
};

GenFutureStates.prototype.apply1 = function (compiler) {
    const self = this;

    console.log(`--------======= GenFutureStates: compiler.outputPath = ${compiler.outputPath}`);


    compiler.plugin('emit', function (compilation, callback) {


        let tmpStates = {};

        // Explore each chunk (build output):
        compilation.chunks.forEach(function (chunk) {

            console.log(`--------======= GenFutureStates: chunk :
                findoutPath=${self.findoutPath(compilation)}
                id=${chunk.id}, 
                name=${chunk.name},
                files=${chunk.files}`);


            // 判断当前 chunk 是否是一个 ui-router 状态
            let curState = statesObj[chunk.name];
            if (!curState) {
                return;
            }
            let stateName = curState[0];

            let jsFile = chunk.files.find((file) => {
                console.log(`--------======= GenFutureStates: file =${file}, typeof = ${typeof file}`);
                return file.startsWith(stateName)
            });
            assert(jsFile, `状态 [${stateName}] 没有找到相应主入口 js 文件。候选有：${chunk.files}`);

            let jsFileUrl = path.relative(
                    compilation.options.output.path,
                    compilation.options.output.path
                ).split(path.sep).join('/') + jsFile;

            console.log(`--------======= GenFutureStates: jsFileUrl =${jsFileUrl}`);
            tmpStates[stateName] = [
                curState[0],    // ui-router 中 匹配的 状态名称
                curState[1],    // ui-router 中 匹配的 url
                jsFileUrl,      // 相对于 index.html 要加载的 JS 文件的路径
            ];
        });

        let outputPath = compilation.getPath(compiler.outputPath);
        //let stateJsonFile = path.resolve(outputPath, "states.json");
        let stateJsonFile = path.resolve(compilation.options.output.path, "states.json");
        console.log(`--------======= GenFutureStates: stateJsonFile =${stateJsonFile}`);

        //console.log(`--------======= GenFutureStates: outputPath =${outputPath}`);
        fs.writeFileSync(stateJsonFile, JSON.stringify(tmpStates));  // FIXME 目录不存在

        callback();
    });
};

module.exports = exports = GenFutureStates;
