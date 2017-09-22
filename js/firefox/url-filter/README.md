This is a firefox addon
https://servers.ustclug.org/2014/07/ustc-blog-force-google-fonts-proxy/



```
./
./package.json
./README.md
./test
./e2e-tests
./data

fonts.googleapis.com         fonts.lug.ustc.edu.cn
ajax.googleapis.com          ajax.lug.ustc.edu.cn
themes.googleusercontent.com google-themes.lug.ustc.edu.cn
fonts.gstatic.com            fonts-gstatic.lug.ustc.edu.cn

```
page scripts
content script
addon script


# 说明
因为从  Firefox 57 开始， Firefox 只支持以 [WebExtension](https://developer.mozilla.org/zh-CN/Add-ons/WebExtensions/What_are_WebExtensions)
的方式进行开发的插件，所以，使用以前的技术（比如 [Add-on SDK - jpm](https://developer.mozilla.org/en-US/Add-ons/SDK/Tools/jpm#Installation) ）等
都不再支持了。


# 开发

```bash
# 创建 manifest.json, index.js

# firefox 中打开 `about:debugging`，点击 "临时载入附加组件"

cd url-filter               # 根目录
zip -r ../beastify.xpi *    # 打zip包，但后缀是 xpi

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


    ```bash
    npm install --global web-ext
    web-ext --version
    
    cd url-filter
    web-ext run
    
    # 打包
    # 登录 https://addons.mozilla.org/en-US/firefox/ ，在 Tools/Manager API keys 下找到你的 key
    AMO_JWT_ISSUER=
    AMO_JWT_SECRET=
    web-ext sign --api-key=$AMO_JWT_ISSUER --api-secret=$AMO_JWT_SECRET
    ```




 
 