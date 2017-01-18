

[system.js](https://github.com/systemjs/systemjs)
是一个 Universal dynamic module loader。


```bash
# npm install -g traceur-cli
npm install -g traceur
mkdir .tmp
traceur --out .tmp/load-esm.html.aaa.js --moudle load-esm.html.aaa.js

npm install jspm -g
npm install --save-dev jspm 
jspm init -p    # 其中有一个步骤用以选择用哪种 ES6 transpiler.

# 打包，需要明确使用脚本去 import
# FIXME 如果生成的css在子目录下，目前无论怎么设置 separateCSS, buildCSS, baseURL, baseURL 都出问题
jspm bundle --minify angular1-app.js bundle.js 

# FIXME 如何线上环境打包时，只打包所需的？还是依靠CDN 按需回源站？
```

## 示例

* load-amd.html  测试加载 AMD 模块
* load-cmd.html  测试加载 CMD 模块
* load-esm.html  测试加载 ES6 模块
* load-js.html   测试加载普通的js文件。
