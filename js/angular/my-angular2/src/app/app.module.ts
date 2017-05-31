import "./rxjs-extensions";
import {enableProdMode, NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {InMemoryWebApiModule} from "angular-in-memory-web-api";
import {InMemoryDataService} from "./in-memory-data.service";

import {AppRoutingModule} from "./app-routing.module";

import {AppComponent} from "./app.component";
// import {HeroService} from "./hero.service";
// import {HeroSearchComponent} from "./hero-search.component";

// enableProdMode();

// https://angular.cn/docs/ts/latest/guide/architecture.html
@NgModule({

  // 本模块声明的组件模板需要的类所在的其它模块
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService, {delay: 600}),
    AppRoutingModule
  ],


  // 声明本模块中拥有的视图类。Angular 有三种视图类：组件、指令和管道。
  declarations: [
    AppComponent,
    // HeroSearchComponent//,
    //routedComponents
  ],

  // declarations 的子集，可用于其它模块的组件模板
  exports: [],

  // 服务的创建者，并加入到全局服务列表中，可用于应用任何部分
  providers: [
    // HeroService
  ],

  // 指定应用的主视图（称为根组件），它是所有其它视图的宿主。只有根模块才能 配置在此处。
  bootstrap: [AppComponent]
})
export class AppModule {

}

