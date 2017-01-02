
https://www.typescriptlang.org


```
npm install -g typescript
npm install --save-dev gulp gulp-typescript 
npm install --save-dev babel-cli babel-preset-latest babel-polyfill gulp-babel

gulp 
node dist/ts/class-01.js
# 大部分都可以直接使用 node 命令运行。

# 至于浏览器中，因为 TypeScript 的 async 只翻译到 ES6 级别，
# 如果要在浏览器内运行，还需要使用 BabelJs 再翻译一遍
# 即 TS => Babel => ES5

```