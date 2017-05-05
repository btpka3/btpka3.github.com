

# 参考

* [Python 3 教程](http://www.runoob.com/python3/python3-tutorial.html)



# 工具
* pip 一个 installer 程序。从 python 3.4 后自带
* pyvenv 用以创建虚拟环境
* PyPI - the Python Package Index。收录各种包
* virtualenv 一个 pyvenv 的替代品，支持 python 2.


# pip

[pip](https://pip.pypa.io/en/stable/)

## 常用命令

```bash
pip completion --bash >> ~/.profile # bash 自动完成
pip search "query"                  # 搜索
pip show sphinx                     # 显示特定包的详情

pip install SomePackage             # 安装最新版本
pip install SomePackage==1.0.4      # 安装指定版本
pip install 'SomePackage>=1.0.4'    # 安装最小版本
pip3 install --upgrade SomePackage  # 更新至最新版

pip uninstall SomePackage           # 卸载

pip install --upgrade pip           # pip 自升级

pip list                            # 列出安装了哪些包
pip list --outdated                 # 列出已安装、但已过期的包
```

## 配置

1. 系统级别
    * Linux : `/etc/pip.conf`，
       环境变量 `XDG_CONFIG_DIRS` 下 `pip` 子目录。
       比如: `/etc/xdg/pip/pip.conf`
    * MacOS : `/Library/Application Support/pip/pip.conf`
    * Windows XP : `C:\Documents and Settings\All Users\Application Data\pip\pip.ini`
    * Windows 7+ : `C:\ProgramData\pip\pip.ini`
    
    
1. 用户级别
    * Linux : `$HOME/.config/pip/pip.conf`
    * MacOS : `$HOME/Library/Application Support/pip/pip.conf`
    * Windows : `%APPDATA%\pip\pip.ini`
    
    * 遗留的用户级配置文件
    
        * Linux/MacOS : `$HOME/.pip/pip.conf`
        * Windows : `%HOME%\pip\pip.ini`

    * 可以通过环境变量 `PIP_CONFIG_FILE` 指定配置文件的位置

1. virtualenv 级别
    * Linux/MacOS : `$VIRTUAL_ENV/pip.conf`
    * Windows : `%VIRTUAL_ENV%\pip.ini`

示例配置：

```ini
[global]
timeout = 60
index-url = http://download.zope.org/ppix
no-cache-dir = false
find-links =
    http://download.example.com

[install]
ignore-installed = true
no-dependencies = yes
no-compile = no
find-links =
    http://mirror1.example.com
    http://mirror2.example.com

[freeze]
timeout = 10
```

# wheel

[wheel](https://wheel.readthedocs.io/en/latest/)


```bash
pip install wheel
pip install SomePackage-1.0-py2.py3-none-any.whl
```