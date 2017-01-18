

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
```

## 示例

* load-amd.html  测试加载 AMD 模块
* load-cmd.html  测试加载 CMD 模块
* load-esm.html  测试加载 ES6 模块
* load-js.html   测试加载普通的js文件。
