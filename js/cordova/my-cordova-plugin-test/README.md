

```
cordova create my-cordova-plugin-test
cd my-cordova-plugin-test/
cordova platform add --save browser
cordova platform add --save android
cordova platform add --save ios
cordova requirements
cordova plugin add --save ../my-cordova-plugin

# 每当 my-cordova-plugin 的代码有变动时, 最方便的方法当属以下
rm -fr platforms plugins
cordova prepare
cordova run browser

```