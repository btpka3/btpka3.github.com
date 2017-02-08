// 运行时追加 assets

const fs = require('fs');
const glob = require('glob');
const path = require('path');
const assert = require('assert');
var NodeTemplatePlugin = require('webpack/lib/node/NodeTemplatePlugin');
var NodeTargetPlugin = require('webpack/lib/node/NodeTargetPlugin');
var LoaderTargetPlugin = require('webpack/lib/LoaderTargetPlugin');
var LibraryTemplatePlugin = require('webpack/lib/LibraryTemplatePlugin');
var SingleEntryPlugin = require('webpack/lib/SingleEntryPlugin');


class GenFutureStates {
    constructor(options) {
        console.log('--------new GenFutureStates')
        if (options && options.htmlWebpackPlugin) {
            this.htmlWebpackPlugin = options.htmlWebpackPlugin
        }
    }
}


// 构建额外的资源
function buildExtraAssets(compilation) {
    let arr = []
    compilation.chunks.forEach(function (chunk) {
        console.log(`--------======= GenFutureStates: chunk :
                id=${chunk.id}, 
                name=${chunk.name},
                files=${chunk.files}`);
        arr.push(chunk.files[0])
    });
    fs.writeFileSync("./src/myplugin.js", "var mypluginData=" + JSON.stringify(arr) + ";");
}

function genChildCompiler(compilation) {

    // 追加额外的资源
    // http://stackoverflow.com/questions/28987626/using-a-loader-inside-a-webpack-plugin/30344170#30344170
    var outputOptions = {
        //filename: "ttt.js",
        filename: '[name].[hash].js',
        publicPath: compilation.outputOptions.publicPath
    };
    var compilerName = "myAsset";

    var childCompiler = compilation.createChildCompiler(compilerName, outputOptions);
    //childCompiler.outputFileSystem = compiler.outputFileSystem;
    childCompiler.apply(
        new NodeTemplatePlugin(outputOptions),
        new NodeTargetPlugin(),
        new LibraryTemplatePlugin('result', 'var'),
        new SingleEntryPlugin(this.context, "expose-loader?mypluginData!exports-loader?mypluginData!./src/myplugin.js", "myplugin"),
        //new SingleEntryPlugin(this.context, "raw-loader!./src/myplugin.js", "myplugin"),
        // new SingleEntryPlugin(this.context, "script-loader!./src/myplugin.js", "myplugin"),
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
                    console.log("--------------------extraAssets =  ", extraAssets);
                    Array.prototype.push.apply(htmlPluginData.assets.js, extraAssets);
                    htmlPluginData.assets.js.push("../src/myplugin-2.js");
                    console.log("--------------------htmlPluginData.assets.js =  ", htmlPluginData.assets.js);
                    callback(null, htmlPluginData);
                },
                function (err) {
                    console.log("--------err", err);
                    callback(err);
                }
            )
            // callback(null, htmlPluginData);
        });
    });

    // 所需的资源都编译好，准备发布时, 生成额外的资源
    compiler.plugin('emit', function (compilation, callback1) {

        aaa = new Promise(function (resolve, reject) {

            // 先生成额外的资源
            let extraAssets = buildExtraAssets(compilation);

            // 再构建
            let childCompiler = genChildCompiler(compilation);
            childCompiler.runAsChild(function (err, entries, childCompilation) {

                console.log("------childCompiler-err", err);

                var arr = []
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

module.exports = exports = GenFutureStates;
