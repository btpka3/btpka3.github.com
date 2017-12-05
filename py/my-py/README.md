

# 参考

- [Python 3 教程](http://www.runoob.com/python3/python3-tutorial.html)
- [Python2.x与3​​.x版本区别](http://www.runoob.com/python/python-2x-3x.html)
- [Cheat Sheet: Writing Python 2-3 compatible code](http://python-future.org/compatible_idioms.html)
- [What’s New In Python 3.0](https://docs.python.org/3.0/whatsnew/3.0.html)
- [python-packaging](https://python-packaging.readthedocs.io/en/latest/minimal.html)
- http
    - [http](https://docs.python.org/3/library/http.html)
    - [urllib](https://docs.python.org/3/library/urllib.html)
    - [urllib3](https://pypi.python.org/pypi/urllib3)
    - [python-requests](http://docs.python-requests.org/en/latest/index.html)
- hmac
    - [hmac](https://docs.python.org/2/library/hmac.html)

# 工具
* pip 一个 installer 程序。从 python 3.4 后自带
* pyvenv 用以创建虚拟环境
* PyPI - the Python Package Index。收录各种包
* virtualenv 一个 pyvenv 的替代品，支持 python 2.





# pip

[pip](https://pip.pypa.io/en/stable/)


## 常用命令

```bash
# MacOs 安装
brew search     python3
brew list       python3
brew install    python3
brew upgrade    python3

# pycharm 配置

vi ~/.pycharmrc
source ~/.bashrc
source ~/pycharmvenv/bin/activate

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

pip3 install PyQt5
/usr/local/Cellar/python3/3.6.3/bin/pip3 install virtualenv

pip3 install aliyun-python-sdk-alidns
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

# virtualenv


```bash
pip3 install virtualenv

mkdir myproject
cd myproject/

# 创建一个环境 venv (不复制系统级别的安装包，也就是说是全新的）
virtualenv -p /usr/local/Cellar/python3/3.6.3/bin/python3 \
    --no-site-packages \
    env-dev

# 使用该环境
source env-dev/bin/activate

# 退出该环境
deactivate
```

# pyvenv

```bash

# 创建一个新的环境
pyvenv env_dev
tree -L 3
.
└── env_dev
    ├── bin
    │   ├── activate
    │   ├── activate.csh
    │   ├── activate.fish
    │   ├── easy_install
    │   ├── easy_install-3.5
    │   ├── pip
    │   ├── pip3
    │   ├── pip3.5
    │   ├── python -> python3.5
    │   ├── python3 -> python3.5
    │   └── python3.5 -> /usr/local/Cellar/python3/3.5.2_1/Frameworks/Python.framework/Versions/3.5/bin/python3.5
    ├── include
    ├── lib
    │   └── python3.5
    └── pyvenv.cfg

# 使用指定的环境
source env_dev/bin/activate

(env_dev) zll@mac-pro ddd$ which python
/private/tmp/ddd/env_dev/bin/python

# 退出环境
(env_dev) zll@mac-pro ddd$ deactivate
```
