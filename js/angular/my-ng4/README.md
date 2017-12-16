
# 参考

- [Angular 2 SASS](http://www.angulartypescript.com/angular-2-sass/)
- [Angular Universal](https://github.com/angular/universal)
- [Protecting Routes using Guards in Angular](https://blog.thoughtram.io/angular/2016/07/18/guards-in-angular-2.html)


- angular 
    - [Can't bind to 'ngforOf' since it isn't a known native property](https://stackoverflow.com/a/35531251/533317) 
    - [Angular 2/4 Component Styles :host, :host-context, /deep/ Selector Example](https://www.concretepage.com/angular-2/angular-2-4-component-styles-host-host-context-deep-selector-example)
    - [Component Styles](https://angular.io/docs/ts/latest/guide/component-styles.html)

- angular-cli
    - [Angular-cli is --mobile still an option?](https://github.com/angular/angular-cli/issues/5791)
    - [Angular CLI Config Schema](https://github.com/angular/angular-cli/wiki/angular-cli)
    - [ng build](https://github.com/angular/angular-cli/blob/master/docs/documentation/build.md)

- `@angular/flex-layout`
    - [demo source](https://github.com/angular/flex-layout/tree/master/src/demo-app)
    - [Fast Starts](https://github.com/angular/flex-layout/wiki/Fast-Starts)
    - [router-outlet issues](https://github.com/angular/flex-layout/issues/171)
      `router-outlet` 不是容器元素，它只是一个占位符，内容都会和它平级。
      可以通过 `@Component({host: {class: 'myClass'}}` 为自动创建的元素添加额外属性。
      也可以通过 `@HostBinding('style.height') appRootHeight = '100%';` 设置。
      或者通过 css `:host` 进行设置。 组件根元素 会有 `_nghost-c1` 属性。该组件所有子元素（不含shadow内容)
      都会有 `_ngcontent-c1` 属性
    
    
- `material`
    - [@angular/material](https://material.angular.io/)
    - [material.io](https://material.io/)
    - [material.io framework-examples](https://github.com/material-components/material-components-web/tree/master/framework-examples/)
    - 通过 `/deep/` 前缀来避免 angular 编译css时，只影响当前组件，不影响子级组件
    
    

- ionic
    - [ionic](https://ionicframework.com/)
    - [Using Ionic 2 with the Angular CLI](https://labs.encoded.io/2016/11/12/ionic2-with-angular-cli/)
    - [Ionic 2 + @angular/cli Seed Project](https://github.com/lathonez/clicker)
    - [Using Angular2 Router in ionic2](https://stackoverflow.com/questions/40459918/using-angular2-router-in-ionic2)
    - [Ionic 3 and Webpack 2 warning](https://github.com/ionic-team/ionic/issues/11072)
    - [Ionic2: Can't find any information on how to implement URL routing](https://github.com/ionic-team/ionic/issues/5479)
    - [Ionic 2 URL routing](http://plnkr.co/edit/mpWU1kG8RXUqVuqs1K5w?p=preview)
    - [jvitor83/angular-pwa-seed](https://github.com/jvitor83/angular-pwa-seed/tree/master/src)
    - [Ion-content hidden when using angular 2 router-outlet](https://forum.ionicframework.com/t/ion-content-hidden-when-using-angular-2-router-outlet/75935)

- primeng
    - [PrimeNG](https://github.com/primefaces/primeng)
    
- clarity
    - [Get Start](https://vmware.github.io/clarity/get-started)

- onsen UI
    - [Angular2+](https://onsen.io/v2/guide/angular2/)
    
- ngx-weiui
    - [weiui](https://weui.io/)
    - [ngx-weiui](https://cipchk.github.io/ngx-weui)

    
- webcomponents
    - [custom-elements](https://github.com/webcomponents/custom-elements)
    - [whatwg - custom-elements](https://html.spec.whatwg.org/multipage/custom-elements.html#custom-elements)
    - [angular-polymer](https://github.com/platosha/angular-polymer)
    - [Using Custom Elements in Angular](https://alligator.io/angular/using-custom-elements/)

- 7788
  - [dragula](https://github.com/bevacqua/dragula)、
    [ng2-dragula](https://github.com/valor-software/ng2-dragula)
  - [ng2-dnd](https://github.com/akserg/ng2-dnd)

# 注意事项

* 使用 yarn 来取代 npm。
* Onsen UI 使用之后， clarity 出错 "Error: The custom element being constructed was not registered with `customElements`."
    且出错后中断路由。
    该问题 如果引入 `../node_modules/@webcomponents/custom-elements/custom-elements.min.js` 会出现
    但如果引入 "../node_modules/@webcomponents/custom-elements/src/custom-elements.js", 则不会报错，但是 字体图标未加载
* OnsenModule 因为re-export 了BrowserModule，故只能在顶层route中使用，子route中数据无法双向绑定. 
  


```bash
ng new \
  --routing true \
  --style scss \
  --skip-install \
  xxx
  
ng serve
```


# css


```html

<app-root _nghost-c0="" ng-version="4.4.6" style="height: 100%; width: 100%; display: flex;" role="admin1" class="fxFlex11">
  <router-outlet _ngcontent-c0=""></router-outlet>
  
  <!-- 自定义组件 `:host {...}` -> `[_nghost-c1]{...}` -->
  <!-- 自定义组件 `.fxFlex22 {...}` -> `[_nghost-c0].fxFlex22 {...}` -->
  <ng-component class="myClass fxFlex22" _nghost-c1="" role="admin" style="display: flex; box-sizing: border-box; flex-direction: column;">
    <mat-toolbar _ngcontent-c1="" class="mat-toolbar mat-primary" color="primary" role="toolbar" ng-reflect-color="primary">
      <div class="mat-toolbar-layout">
        <mat-toolbar-row class="mat-toolbar-row">
          <span _ngcontent-c1="">My Application</span>
          <span _ngcontent-c1="" class="example-spacer" ng-reflect-class-base="example-spacer"></span>
          <mat-icon _ngcontent-c1="" class="mat-icon material-icons" role="img" aria-hidden="true">verified_user</mat-icon>
          <mat-icon _ngcontent-c1="" class="mat-icon material-icons" role="img" aria-hidden="true">face</mat-icon>
        </mat-toolbar-row>
      </div>
    </mat-toolbar>
    <div _ngcontent-c1="" class="aaa" fxflex="" style="flex: 1 1 1e-9px; box-sizing: border-box;" ng-reflect-flex="" ng-reflect-class-base="aaa">sss</div>
    <div _ngcontent-c1="" class="bbb" fxflex="" style="flex: 1 1 1e-9px; box-sizing: border-box;" ng-reflect-flex="" ng-reflect-class-base="bbb">sss</div>
  </ng-component>
</app-root>

<!--

`html /deep/ span` css 选择器会选择 两个 span。
`::shadow span` 则只会选择 inner span.
-->
<div>
  <span>Outer</span>
  #shadow-root
  <my-component>
    <span>Inner</span>
  </my-component>
</div>
```

# FIXME

* sticky 路由？

* 如何使用 OAuth2？

  参考 : [angular-oauth2-oidc](https://github.com/manfredsteyer/angular-oauth2-oidc)

    - 如何在去 oauth 认证前，追加额外参数
    - 如何捕获 oauth 初始认证请求？

* angular 如何配置，才能使 编译后的 js ，css等资源放到 index.html 子目录下？

    参考 [angular-cli Project assets](https://github.com/angular/angular-cli/wiki/stories-asset-configuration)
 
* 如何监听路由跳转？中止路由？


* 如果使用 [PathLocationStrategy](https://angular.io/api/common/PathLocationStrategy)
  到路由后的页面不能刷新，一刷新就404，如何处理？
  
  思路：映射所有请求到 dist/index.html 
  
  那 js、css、图片、字体等静态资源咋办？
  
    - 使用独立cdn域名
    - nginx 等服务器 等保留 assert 路径映射。

        ```
        # 所有静态资源文件都提前映射
        location /qh/assets/ {
          alias /path/to/dist/;
        }
        
        # 所有路由到转移到 index.html
        location /qh {
          alias /path/to/dist/index.html;
        }
        
        ```
  注意：如果使用 PathLocationStrategy，因为 index.html 可能通过不同层级的URL返回，
  所以，其引用的静态资源只能使用绝对路径了(即 `<base href="/abs/path">`)。


* 如何引入 [PrimeFaces](https://www.primefaces.org/primeng/#/)?

* 如何引入 [Ionic](http://ionicframework.com/docs/)

* 是否支持引入外部配置文件？——环境信息独立到构建的包之外。

* i18n 如何支持运行时切换语言？


* `Can't resolve './app/app.module.ngfactory'`
  
    - 删除 node_modules
    - `ncu -u` 更新 package.json
    - `yarn` 重新安装
    - `ng build --prod`

# service worker

@angular/service-worker 的相关文档，在 angular.cn 上还没有更新，只能去[官网](https://angular.io/guide/service-worker-intro)查看.

新建项目时 可以通过 `ng new --service-worker yourProjectName` 添加。
老项目则通过以下步骤完成

- `yarn add @angular/service-worker`
- `ng set apps.0.serviceWorker=true`

- `vi src/app/app.module.ts`
  
  ```ts
  import { ServiceWorkerModule } from '@angular/service-worker';
  import { environment } from '../environments/environment';
  @NgModule({
    imports: [
      // ...
      ServiceWorkerModule.register('/ngsw-worker.js', {enabled: environment.production})
    ],
  }
  })
  export class AppModule { }
  ```

- `vi src/ngsw-config.json`

  ```json
  {
    "index": "/index.html",
    "assetGroups": [
      {
        "name": "app",
        "installMode": "prefetch",
        "resources": {
          "files": [
            "/favicon.ico",
            "/index.html"
          ],
          "versionedFiles": [
            "/*.bundle.css",
            "/*.bundle.js",
            "/*.chunk.js"
          ]
        }
      },
      {
        "name": "assets",
        "installMode": "lazy",
        "updateMode": "prefetch",
        "resources": {
          "files": [
            "/assets/**"
          ]
        }
      }
    ]
  }
  ```
- `ng build --prod`
- `http-server dist/`

测试：

- 先启动服务器访问
- 再关闭服务访问
- chrome 浏览器中访问 `chrome://inspect/#service-workers` 


检查更新， 请查看 [Service Worker doesn't get updated #7665](https://github.com/angular/angular-cli/issues/7665)
`vi src/app/app.component.ts` :

```js
import { SwUpdate } from '@angular/service-worker';
export class AppComponent implements OnInit {
  constructor(private swUpdate: SwUpdate) { }
  ngOninit() {
    this.swUpdate.available.subscribe(event => {
      console.log('A newer version is now available. Refresh the page now to update the cache');
    });
    this.swUpdate.checkForUpdate()
  }
}
```


# MyNg4

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 1.0.6.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `-prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).
Before running the tests make sure you are serving the app via `ng serve`.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).


