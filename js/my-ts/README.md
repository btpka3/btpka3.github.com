
https://www.typescriptlang.org
https://github.com/DefinitelyTyped/DefinitelyTyped

```

npm install -g typescript
npm install --save-dev typescript
npm install --save rxjs
npm install --save-dev @types/rx
npm install --save-dev @types/node
npm install --save-dev gulp gulp-typescript 
npm install --save-dev babel-cli babel-preset-latest babel-polyfill gulp-babel

npm install --save \
    rxjs


npm install --save-dev \
    typescript \
    babel-cli \
    babel-preset-env \
    babel-polyfill \
    gulp \
    gulp-babel \
    gulp-typescript
 
npm install --save-dev \
    @types/rx \
    @types/es6-shim \

#编译单个文件（不使用 tsconfig.json)
#tsc src/rx/test01.ts

#多文件编译
tsc -p .

#编译的模块是 commonjs，所以可以node直接执行
node dist/ts/rx/test01.js 
 
 

# 命令行下运行
tsc xxx.ts

# 通过 IDEA Inetllij 运行
# https://www.jetbrains.com/help/idea/running-typescript.html
# Intellij IDEA -> Preferences -> Language & Frameworks -> TypeScript
#    选中 Enable Typescript Compiler
#    选中 track change
# 创建/修改 ts 文件后，会自动编译成 js 文件
# 打开相应的 js 文件，并右键运行

gulp 
node dist/ts/class-01.js
# 大部分都可以直接使用 node 命令运行。

# 至于浏览器中，因为 TypeScript 的 async 只翻译到 ES6 级别，
# 如果要在浏览器内运行，还需要使用 BabelJs 再翻译一遍
# 即 TS => Babel => ES5

```


## @types 包
 
Typescript 2 之后，提供了 [@types](https://github.com/DefinitelyTyped/DefinitelyTyped)，
可以不再写 `tsd.json` 或者 `typings.json`


- 先安装 js 包     `npm install --save `
- 再通过 typed 定义 `npm install --save @types/lodash`  安装指定的 typed。
- 可以到 [这里](http://microsoft.github.io/TypeSearch/) 搜索相关的 定义。 

- 建议：通过 npm-check-updates 检查安装包的版本 

    ```bash
    npm i -g npm-check-updates
    
    npm ls
    ncu -a && npm i
    ```

