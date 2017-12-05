

## 创建步骤

参考 [angular-phonecat](https://github.com/angular/angular-phonecat)


```
npm config set registry http://registry.npm.taobao.org/
npm init

npm install -g nodemon
npm install -g grunt-cli
npm install --save-dev grunt
npm install --save-dev grunt-html2js
npm install --save-dev grunt-contrib-clean
npm install --save-dev grunt-contrib-compress
npm install --save-dev grunt-contrib-concat
npm install --save-dev grunt-contrib-connect
npm install --save-dev grunt-contrib-cssmin
npm install --save-dev grunt-contrib-jshint
npm install --save-dev grunt-contrib-htmlmin
npm install --save-dev grunt-contrib-nodeunit
npm install --save-dev grunt-contrib-uglify
npm install --save-dev grunt-contrib-watch
npm install --save-dev grunt-bower-task
npm install --save-dev grunt-contrib-less
npm install --save-dev grunt-filerev
npm install --save-dev grunt-usemin
npm install --save-dev time-grunt

npm install --save express
npm install --save jade

npm update

sudo npm install -g bower
bower init
vi .bowerrc

bower install --save jquery#1.11.1
bower install --save angular
bower install --save angular-resource
bower install --save angular-ui
bower install --save angular-route
bower install --save angular-ui-router
bower install --save bootstrap
bower install --save angular-ui-ace\#bower

```

## 目录结构说明


```
src/assets/     # 图片，图标
src/css/        # css
src/app/${domain}/${domain}${actioni}Controller.js
src/app/${domain}/${domain}Service.js

src/
src/assets/                                 # 图片，图标
src/less/                                   # css
src/app/controllers/xxx.js                  # state, controller
src/app/services/xxxService.js              # 服务
src/app/views/xxx/yyy.html                  # html模板片段
src/app/test.js                              # angularjs 入口


target/dist/assets                          # 工程相关的 img、font
target/dist/css                             # 工程相关的 css 代码。
target/dist/js                              # 工程相关的 angularjs 代码。
target/dist/libs                            # 第三方提供的js、css、font、img
target/work/${gruntTask}.${subTaskName}/    # grunt子任务的临时工作目录
target/${pkg.name}.tar.gz                   # 最终发布包
```



# 运行 http 服务器和 mock 数据

```
nodemon mock/test.js           # 独立命令行窗口：启动 mock api
grunt ; grunt watch:all         # 独立命令行窗口：监测文件修改
                                # 通过浏览器访问  http://localhost:3000/
```







https://github.com/focusaurus/express_code_structure