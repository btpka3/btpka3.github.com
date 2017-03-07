该代码仓库主要用于个人总结一些新技术、框架学习用的demo。wiki就迁移到了 [here](https://btpka3.gitbooks.io/btpka3)。

给自己的提醒：

* "contributions calendar" 中没有显示自己的提交。

    这个需要参考[这里](https://help.github.com/articles/why-are-my-contributions-not-showing-up-on-my-profile/)， 需要确保：

    * commit 是最近半年内提交的。
    * email 地址是你的 GitHub 帐号相关联的

        ```sh
        git config --list                      # 检查你的配置项
        git config user.name "btpka3"          # 修改用户名设置
        git config user.email btpka3@163.com   # 修改邮箱
        ```
    * commit 是在一个独立的仓库中提交的，而不是一个 fork 的仓库
    * commit 是在默认分支上（通常是 `master` ）提交的，或者在 `gh-pages`（[Project Pages](https://help.github.com/articles/user-organization-and-project-pages/#project-pages)） 分支上提交的
