
# 更新流程
1. 字体文件有更新的都通知小卉，由她负责更新到 `qh-wap-ui/iconmoon`。
1. 每次应当从 `qh-wap-ui/iconmoon` 目录下获取最新，并迁移到 `qh-wap-front/src/lib/ks-iconfont/` 下面。
    迁移时，需要将字体文件重命名，并追加上日期编号，防止缓存。并修改 sytle.css 文件
1. style.css 文件中追加 

    ```
    width: 1em;
    height: 1em;
    font-size: 24px;
    ```
1. 重新执行 `gulp lib.less` 并提交更新。
