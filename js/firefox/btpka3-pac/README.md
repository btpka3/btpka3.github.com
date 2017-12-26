# 目的

一个浏览器插件，方便自定义 PAC 文件。
 
- 定义并列表显示 代理服务器列表
- 基于单个代理服务器，组合代理服务器尝试列表。
- 定义并列表显示 要代理的域名列表，并为每个域名配置 服务器尝试列表
- 配置信息可以导出，PAC文件可以导出。


# design

```text
基本代理: 列表
  - `DIRECT`
  - `PROXY host:port`
  - `SOCKS host:port`
  - `HTTP host:port`
  - `HTTPS host:port`
  - `SOCKS4 host:port`
  - `SOCKS5 host:port`
  - [{
        type: "http"|"https|"socks4"|"socks"|"direct",  // 其中 socks 代表 socks5
        host: "",
        port: xxx,
        username: "",   // 仅仅当 type = 'socks' 时有用
        password: "",   // 仅仅当 type = 'socks' 时有用
        proxyDNS: false,
        failoverTimeout: 1,  // 超时时间，超时后会使用下一个代理。
    }]
组合代理:
    - mat-select, 多选，option 拖拽排序  
    - chip，可以拖拽排序， 
域名配置
    - 优先级 (拖拽排序) 验证
    - 代理、组合代理
    
    
background_script   : 对应主应用
content_script      : 作为资源文件
proxxy_script      : 作为资源文件
```

# 开发

```bash 

ssh root@122.225.11.207 -C -f -N -g -D 9999


npm install --global web-ext
web-ext --version


ng new \
  --routing true \
  --style scss \
  --skip-install \
  btpka3-pac
  
ng build

cd btpka3-pac
web-ext --browser-console --firefox=nightly run 
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

-   [TypeSearch](https://microsoft.github.io/TypeSearch/)
-   [web-ext-types](https://github.com/kelseasy/web-ext-types)
-   [Proxy Auto-Configuration (PAC) file](https://developer.mozilla.org/en-US/docs/Web/HTTP/Proxy_servers_and_tunneling/Proxy_Auto-Configuration_(PAC)_file)
