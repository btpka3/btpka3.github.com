

## 创建步骤

```
npm config set registry http://registry.npm.taobao.org/
npm init

npm install --save express
npm install --save jade

npm install -g grunt-cli
npm install --save-dev grunt
npm install --save-dev grunt-html2js
npm install --save-dev grunt-contrib-clean
npm install --save-dev grunt-contrib-connect
npm install --save-dev grunt-contrib-compress
npm install --save-dev grunt-contrib-concat
npm install --save-dev grunt-contrib-jshint
npm install --save-dev grunt-contrib-htmlmin
npm install --save-dev grunt-contrib-less
npm install --save-dev grunt-contrib-nodeunit
npm install --save-dev grunt-contrib-uglify
npm install --save-dev grunt-contrib-watch
npm install --save-dev grunt-contrib-cssmin
npm install --save-dev grunt-contrib-connect
npm install --save-dev grunt-filerev



npm install --save-dev grunt-bower-task

//npm install --save-dev grunt-bower
npm install --save-dev grunt-usemin

npm install -g bower
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
bower install --save angular-ui-bootstrap
```

## 目录结构说明


```

src/
src/assets/     # 图片，图标
src/less/       # css
src/app/${domain}/${domain}Controller.js
src/app/${domain}/${domain}Service.js
src/app/${domain}/${domain}Service.js

target/dist/js
target/dist/libs
target/dist/css
target/dist/img
target/work/${gruntTask}.${subName}/
target/${pkg.name}.tar.gz






src/less/vendor-prefixes.less           # 从 bootstrap 拷贝过来
```



















