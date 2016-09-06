

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


# 运行 android 的话, 可以
1. 命令行: `cordova run android`
2. 通过 Android Studio : File : New : Import Project ... : ./platforms/android 进行debug
3. 无论上面哪种方式,都可以通过 Chrome 浏览器 `chrome://inspect/#devices` 来远程 debug。

```