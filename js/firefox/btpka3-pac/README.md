# 目的

一个浏览器插件，方便自定义 PAC 文件。
 
- 定义并列表显示 代理服务器列表
- 基于单个代理服务器，组合代理服务器尝试列表。
- 定义并列表显示 要代理的域名列表，并为每个域名配置 服务器尝试列表
- 配置信息可以导出，PAC文件可以导出。
 
# 开发

```bash 
npm install --global web-ext
web-ext --version


ng new \
  --routing true \
  --style scss \
  --skip-install \
  btpka3-pac

cd btpka3-pac
web-ext run --browser-console
web-ext --firefox=nightly run

# 打包
web-ext build --source-dir ./dist

# 签名
# 登录 https://addons.mozilla.org/en-US/firefox/ ，在 Tools/Manager API keys 下找到你的 key
WEB_EXT_API_KEY=
WEB_EXT_API_SECRET=
web-ext sign                # --api-key=$WEB_EXT_API_KEY --api-secret=$WEB_EXT_API_SECRET
```

# 参考

-   [Content scripts](https://developer.mozilla.org/en-US/Add-ons/WebExtensions/Content_scripts)
    可以直接修改网页内容，同网页中 `<script>` 加载的 js 一样。
    
    Content scripts 只能访问很少一部分 WebExtension JavaScript API。但是它可以 
    [与 Background scripts 进行通信](https://developer.mozilla.org/en-US/Add-ons/WebExtensions/Content_scripts#Communicating_with_background_scripts)
    
    FIXME: 如何让JS在网页内载入特定的 资源（css，字体，图片等）。

-   [Background scripts](https://developer.mozilla.org/en-US/Add-ons/WebExtensions/Background_scripts) 可以访问
    所有 [WebExtension JavaScript APIs](https://developer.mozilla.org/en-US/Add-ons/WebExtensions/API)，
    但是无法直接访问网页的内容。

-   [web-ext](https://developer.mozilla.org/zh-CN/Add-ons/WebExtensions/Getting_started_with_web-ext) 

-   [WebExtensions/API/proxy](https://developer.mozilla.org/en-US/Add-ons/WebExtensions/API/proxy)
