

# 目的 
- 学习 [karma](http://karma-runner.github.io/2.0/intro/configuration.html)


# 步骤

```bash
mkdir my-karma
cd my-karma
npm init
yarn add -D                 \
    karma                   \
    karma-jasmine           \
    karma-chrome-launcher   \
    jasmine-core            \
    requirejs               \
    karma-coverage          \
    karma-sourcemap-loader  \
    karma-remap-istanbul

npm install -g karma-cli
karma init 

mkdir src
vi src/aaa.js

karma start karma.conf.js
```


# 7788
- karma-coverage 用以报告覆盖率，但是当使用 Babel， Typescript 等编译后后会不准确，可以使用 karma-remap-istanbul 
或其他 karma-typescript（自带了 istanbul 相关功能）来处理

- 点击 "DEBUG" 按钮，通过源代码的方式还是无法 debug 的——看到的是各种 preprocessor 处理后的代码。
。
