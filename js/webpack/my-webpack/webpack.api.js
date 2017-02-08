// 编程运行 webpack
// https://webpack.github.io/docs/node.js-api.html


var webpack = require("webpack");

var MyPlugin = require("./webpack.myPlugin.js");

var compiler = webpack({
    target: "web",
    resolve: {
        modules: [
            'node_modules',
            'bower_components',
        ]
    },
    entry: {
        index: [
            "./src/index.js"
        ],
        // user: [
        //     "./src/user.js"
        // ]
    },
    plugins: [
        new MyPlugin()
    ],
    output: {
        path: './build',
        filename: '[name].[hash].js',
        chunkFilename: '[name].[hash].chunk.js',
        sourceMapFilename: "[id].[hash].[file].map"
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                use: [
                    {
                        loader: 'babel-loader',
                        options: {
                            cacheDirectory: '.tmp'
                        }
                    }
                ],
                exclude: /node_modules/
            }
        ]
    },
});

compiler.run(function (err, stats) {
    console.log("-------err", err)
});

