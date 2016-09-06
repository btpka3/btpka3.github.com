# my-cordova-plugin

This is my playground for my first [cordova plugin](https://cordova.apache.org/docs/en/latest/guide/hybrid/plugins/index.html) .

参考:
 
* 《[Plugin.xml](https://cordova.apache.org/docs/en/latest/plugin_ref/spec.html)》
*  官方参考例子 —— [cordova-plugin-device](https://git-wip-us.apache.org/repos/asf?p=cordova-plugin-device.git;a=tree)
安卓插件的类必须继承自 [org.apache.cordova.CordovaPlugin](https://github.com/apache/cordova-android/blob/master/framework/src/org/apache/cordova/CordovaPlugin.java)



```
npm install -g plugman

plugman install --platform ios --project /path/to/my/project/www --plugin /path/to/my/plugin

# Validating a Plugin using Plugman
cd my-cordova-plugin
plugman createpackagejson /path/to/your/plugin

# 使用 Android Studio 从打开工程  src/android 并编辑Java源代码。

# 在 my-cordova-plugin-test 工程中进行测试
cd ../my-cordova-plugin-test
cordova plugin add --save ../my-cordova-plugin
```


# browser

# android

为了方便能在 android studio中进行编译, 代码提示,
在 src/android/settings.gradle 中引入了 cordova-android 的源代码,
在 src/android/build.gradle 中对其进行了依赖。


