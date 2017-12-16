# 目的
尝试将 ionic3 用在纯网站应用中


```bash
ng new \
  --routing false \
  --style scss \
  --service-worker \
  --skip-install \
  my-ng5-ionic3

cd my-ng5-ionic3
yarn
yarn add ionic-angular

cat >> src/styles.scss <<EOF
@import "~ionic-angular/css/ionic.css";
EOF

# 修改 src/index.html : app-root -> ion-app
# 修改 src/app/* 
# 追加 src/pages/*
  # 要在 @NgModule.declarations 中进行声明
  # 要在 @NgModule.entryComponents 中进行声明
  # 通过 <ion-nav #myNav [root]="rootPage"></ion-nav> 显示默认画面
  # 通过  this.navCtrl.push(UserPage); 切换页面。

```


# 参考
- [ionic-preview-app](https://github.com/ionic-team/ionic-preview-app/)
- [Using Ionic 2 with the Angular CLI](https://labs.encoded.io/2016/11/12/ionic2-with-angular-cli/)
- [jvitor83/angular-pwa-seed](https://github.com/jvitor83/angular-pwa-seed/tree/master/src)
- [Ion-content hidden when using angular 2 router-outlet](https://forum.ionicframework.com/t/ion-content-hidden-when-using-angular-2-router-outlet/75935)
