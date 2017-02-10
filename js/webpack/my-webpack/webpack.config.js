var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require("extract-text-webpack-plugin");
var MyPlugin = require("./webpack.myPlugin.js");

var args = require('yargs').argv;
var path = require('path');
//
// parameters
var isProd = args.env && args.env.prod;
var isMock = args.env && args.env.mock;
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
var appCssPlugin = new ExtractTextPlugin({
    filename: isProd ? '[name].scss.[hash].css' : '[name].scss.css'
});


var mypluginHtmlWebpackPlugin = new HtmlWebpackPlugin({
    template: './src/myplugin.html',
    filename: 'myplugin.html',
    chunks: [],
    // favicon: 'favicon.ico',
    appName: appName,
    hash: !isProd,
    minify: {
        html5: true,
        removeComments: true,
        collapseWhitespace: true
    }
});

var plugins = [
    // new webpack.ProvidePlugin({
    //     $: "jquery",
    //     jQuery: "jquery",
    //     "window.jQuery": "jquery"
    // }),
    new MyPlugin({htmlWebpackPlugin: mypluginHtmlWebpackPlugin}),
    vendorCssPlugin,
    appCssPlugin,
    mypluginHtmlWebpackPlugin,
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
    new HtmlWebpackPlugin({
        template: './src/testJson.html',
        filename: 'testJson.html',
        chunks: ['testJson'],
        // favicon: 'favicon.ico',
        appName: appName,
        hash: !isProd,
        minify: {
            html5: true,
            removeComments: true,
            collapseWhitespace: true
        }
    })
];

if (isProd) {
    plugins.push(
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false
            },
            mangle: {
                except: ['$super', '$', 'exports', 'require']
            },
            output: {
                comments: false
            }
        }),
        new webpack.optimize.OccurrenceOrderPlugin()
    );
}


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
        ],
        testJson: [
            "./src/testJson.js"
        ]
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
            },
            // {
            //     test: /\.scss$/,
            //     loader: appCssPlugin.extract(
            //         {
            //             fallbackLoader: 'style-loader',
            //             loader: [
            //                 {
            //                     loader: 'css-loader',
            //                     // XXX : 需要关注 https://github.com/webpack/css-loader/pull/400
            //                     query: {
            //                         minimize: true,
            //                         sourcemap: false,
            //                         discardComments: {
            //                             removeAll: true
            //                         },
            //                         calc: false
            //                     }
            //
            //                 },
            //                 "sass-loader"
            //             ]
            //         })
            // },
            {
                test: /\.scss$/,
                use: [
                    'style-loader',
                    {
                        loader: 'css-loader',
                        // XXX : 需要关注 https://github.com/webpack/css-loader/pull/400
                        options: {
                            minimize: true,
                            sourcemap: false,
                            discardComments: {
                                removeAll: true
                            },
                            calc: false
                        }
                    },
                    "sass-loader"
                ]
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
                test: /\.(jpg|jepg|png|gif)(\?]?.*)?$/,
                use: [
                    {
                        loader: "file-loader",
                        options: {
                            name: "assets/fonts/[name]-[hash].[ext]"
                        }
                    }
                ]
            }
        ]
    },
    plugins: plugins,
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
};

module.exports = config;
