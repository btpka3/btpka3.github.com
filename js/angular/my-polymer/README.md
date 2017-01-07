

《[Build your first Polymer element](https://www.polymer-project.org/1.0/start/first-element/intro)》


## 自定义元素

```sh

npm install -g bower
npm install -g polymer-cli

mkdir my-ploymer
cd my-ploymer
bower init
bower install --save polymer iron-icon iron-icons

polymer serve --open
# 浏览器访问： http://localhost:8080/src/
```

## 自定义APP

```sh
polymer init starter-kit
polymer serve --open
# 浏览器访问 http://localhost:8080
```


## 文件说明

```txt
./manifest.json       // WPA 应用所需的文件

```