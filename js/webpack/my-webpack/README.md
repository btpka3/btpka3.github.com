

```
npm install -g  webpack@2.2.0
npm install -g webpack-dev-server@2.2.0
npm i

# 打包到 build 目录下
webpack

# 根据 webpack.config.js 启动开发服务器，通过 http://localhost:8080/ 访问。
webpack-dev-server

# 示例：命令下较全的参数配置
webpack-dev-server \
    --progress \
    --colors \
    --open \
    --content-base ./dist \ 
    --host localhost \
    --port 8080 \
    --inline \
    --output-public-path /\
    --client-log-level info
```


##  目的

* 第三方 js,css,字体打包: 比如 bootstrap 的 按钮样式，图标字体。 
* 使用在线CDN JS,css,字体资源等
* 编译 SASS
* 使用 ES 6
