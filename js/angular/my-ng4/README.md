
# 参考

- [Angular 2 SASS](http://www.angulartypescript.com/angular-2-sass/)
- [Angular Universal](https://github.com/angular/universal)
- [Protecting Routes using Guards in Angular](https://blog.thoughtram.io/angular/2016/07/18/guards-in-angular-2.html)


- angular 
    - [Can't bind to 'ngforOf' since it isn't a known native property](https://stackoverflow.com/a/35531251/533317) 
- angular-cli
    - [Angular-cli is --mobile still an option?](https://github.com/angular/angular-cli/issues/5791)

- `@angular/flex-layout`
    - [demo source](https://github.com/angular/flex-layout/tree/master/src/demo-app)
    - [Fast Starts](https://github.com/angular/flex-layout/wiki/Fast-Starts)

- `material`
    - [doc](https://material.angular.io/)

- ionic
    - [Using Ionic 2 with the Angular CLI](https://labs.encoded.io/2016/11/12/ionic2-with-angular-cli/)
    - [Ionic 2 + @angular/cli Seed Project](https://github.com/lathonez/clicker)
    - [Using Angular2 Router in ionic2](https://stackoverflow.com/questions/40459918/using-angular2-router-in-ionic2)
    - [Ionic 3 and Webpack 2 warning](https://github.com/ionic-team/ionic/issues/11072)

- clarity
    - [Get Start](https://vmware.github.io/clarity/get-started)
    
- webcomponents
    - [custom-elements](https://github.com/webcomponents/custom-elements)

# 注意事项

* 使用 yarn 来取代 npm。


```bash
ng new \
  --routing true \
  --style scss \
  --skip-install \
  xxx
```

# FIXME

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


