

```
npm install webpack -g
npm install webpack-dev-server -g

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

# 浏览器访问 http://localhost:8080/

```


##  目的

* 第三方 js,css,字体打包: 比如 bootstrap 的 按钮样式，图标字体。 
* 使用在线CDN JS,css,字体资源等
* 编译 SASS
* 使用 ES 6
