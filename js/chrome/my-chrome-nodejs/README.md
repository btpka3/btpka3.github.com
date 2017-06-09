
# 目的
网页截图

# 参考

* [Using headless Chrome as an automated screenshot tool](https://medium.com/@dschnr/using-headless-chrome-as-an-automated-screenshot-tool-4b07dffba79a)
* [chrome-remote-interface](https://www.npmjs.com/package/chrome-remote-interface)
* [Chrome DevTools Protocol](https://github.com/ChromeDevTools/awesome-chrome-devtools#chrome-devtools-protocol)


# 步骤

```bash
npm init
npm install --save chrome-remote-interface
npm install --save minimist

# 需要 node 7 以上版本
node index.js --url="http://news.163.com"
```