var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require("extract-text-webpack-plugin");

// var CopyWebpackPlugin = require('copy-webpack-plugin');
// var autoprefixer = require('autoprefixer');
var args = require('yargs').argv;
var path = require('path');
//
// parameters
var isProd = args.prod;
var isMock = args.mock;
//
var base = __dirname;
// // use mock api or not
// var entryJs = isMock ?
//     base + 'src/index.js' :
//     base + 'src/index.js';
var appName = isMock ? 'appTest' : 'app';


var vendorCssPlugin = new ExtractTextPlugin({
    filename: isProd ? '[name].[hash].css' : '[name].css'
});
//
// var plugins = [

//     new webpack.DefinePlugin({
//         __PROD__: isProd,
//         __MOCK__: isMock
//     }),
//     new webpack.optimize.CommonsChunkPlugin({
//         //names: ["vendor"],
//         //minChunks: 3,
//         minChunks: Infinity,
//         children: true,
//         async: true
//     }),
//     //
//     vendorCssPlugin,
//
//     // 只针对主页
//     new HtmlWebpackPlugin({
//         template: './src/index.html',
//         chunks: ['app', 'vendor'],
//         // favicon: 'favicon.ico',
//         appName: appName,
//         hash: !isProd,
//         minify: {
//             html5: true,
//             removeComments: true,
//             collapseWhitespace: true
//         }
//     }),
//     // new CopyWebpackPlugin([
//     //     {from: 'node_modules/babel-core/browser-polyfill.min.js', to: 'polyfill.js'}
//     // ])
//
// ];
//
//
// if (isProd) {
//     plugins.push(
//         new webpack.optimize.UglifyJsPlugin({
//             compress: {
//                 warnings: false
//             },
//             mangle: {
//                 except: ['$super', '$', 'exports', 'require']
//             }
//         }),
//         //                   OccurrenceOrderPlugin
//         new webpack.optimize.OccurenceOrderPlugin(),
//         new webpack.NoErrorsPlugin(),
//         new webpack.optimize.DedupePlugin()
//     );
// }


const config = {
    target: "web",
    resolve: {
        modules: [
            'node_modules',
            'bower_components',
        ]
    },
    // externals: {
    //     "https://unpkg.com/jquery@3.1.1": "jQuery"
    // },
    entry: {
        index: [
            "./src/index.js"
        ],
        user: [
            "./src/user.js"
        ]
        // // 第三方类库
        // vendor: [
        //     // 'angular-material/angular-material.css',
        //     // 'angular-material/angular-material.js',
        //     // 'animate.css/animate.css',
        //     //
        //     'angular',
        //     // 'angular-ui-router',
        //     // 'angular-animate',
        //     // 'angular-messages',
        //     // 'angular-mocks',
        //     // 'angular-loading-bar',
        //     // 'oclazyload'
        // ]
    },
    output: {
        path: path.resolve(base, 'build'),
        filename: isProd ? '[name].[hash].js' : '[name].js',
        chunkFilename: isProd ? '[name].[hash].chunk.js' : '[name].chunk.js',
        crossOriginLoading: "anonymous",
        sourceMapFilename: isProd ? "[id].[hash].[file].map" : '[file].map',
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                use: [
                    {
                        loader: 'babel-loader',
                        options: {
                            cacheDirectory: path.resolve(base, '.tmp')
                        }
                    }
                ],
                exclude: /node_modules/
            },
            // {
            //     test: /https:\/\/unpkg\.com\/.*/,
            //     use: [
            //         'script-loader'
            //     ]
            // },
            // {
            //     test: /MyShimmingModule.js$/,
            //     use: [
            //         {
            //             loader: 'imports-loader',
            //             options: {
            //                 this: ">global"
            //             }
            //         }
            //     ],
            //     exclude: /node_modules/
            // },
            // { // 处理 src/app/components/**/*.html, src/app/pages/**/*.html,
            //     test: /\.html$/,
            //     loaders: ['raw']//, 'html-minify'
            // },
            // {
            //     test: /\.scss$/,
            //     loaders: ["style-loader", "css-loader", "sass-loader"]
            // },
            {
                test: /\.css$/,
                loader: vendorCssPlugin.extract({
                    fallbackLoader: 'style-loader',
                    loader: {
                        loader: 'css-loader',
                        // XXX : 需要关注 https://github.com/webpack/css-loader/pull/400
                        query: {
                            minimize: true,
                            sourcemap: false,
                            discardComments: {
                                removeAll: true
                            },
                            calc: false
                        }

                    }
                })
                // use: [ 'style-loader',
                //     {
                //         loader: 'css-loader',
                //         options: { minimize: {safe:true} }
                //     } ]
            },
            {
                test: /\.(woff|woff2|ttf|eot|svg)(\?]?.*)?$/,
                use: [
                    {
                        loader: "file-loader",
                        options: {
                            name: "assets/fonts/[name]-[hash].[ext]"
                        }
                    }
                ]
            },
            {
                test: /\.scss$/,
                use: ["style-loader", "css-loader", "sass-loader"]
            }
            // {
            //     test: /\.html$/,
            //     loader: 'html'
            // }
            // {
            //     test: /\.(png|jpg)$/,
            //     loader: 'url?limit=8192&name=assets/images/[name].[hash].[ext]'
            // }
        ]
    },
    plugins: [
        // new webpack.ProvidePlugin({
        //     $: "jquery",
        //     jQuery: "jquery",
        //     "window.jQuery": "jquery"
        // }),
        vendorCssPlugin,
        new HtmlWebpackPlugin({
            template: './src/index.html',
            filename: 'index.html',
            chunks: ['index'],
            // favicon: 'favicon.ico',
            appName: appName,
            hash: !isProd,
            minify: {
                html5: true,
                removeComments: true,
                collapseWhitespace: true
            }
        }),
        new HtmlWebpackPlugin({
            template: './src/user.html',
            filename: 'test.html',
            chunks: ['user'],
            // favicon: 'favicon.ico',
            appName: appName,
            hash: !isProd,
            minify: {
                html5: true,
                removeComments: true,
                collapseWhitespace: true
            }
        }),
        // new webpack.optimize.UglifyJsPlugin({
        //     compress: {
        //         warnings: false
        //     },
        //     mangle: {
        //         except: ['$super', '$', 'exports', 'require']
        //     },
        //     output: {
        //         comments: false
        //     }
        // }),
        //                   OccurrenceOrderPlugin,OccurenceOrderPlugin
        new webpack.optimize.OccurrenceOrderPlugin(),
        // new webpack.NoErrorsPlugin(),
        // new webpack.optimize.DedupePlugin()
    ],
    //debug: !isProd,
    devtool: "source-map",

    devServer: {
        contentBase: path.resolve(base, 'build'),
        historyApiFallback: true,
        stats: {
            modules: false,
            cached: false,
            colors: true,
            chunk: false
        },
        host: '0.0.0.0',
        port: 8080,
        inline: true,
        hot: false,
        clientLogLevel: "info",
        compress: true,
        quiet: false
    },
    // postcss: function () {
    //     return [autoprefixer];
    // }

};

module.exports = config;
