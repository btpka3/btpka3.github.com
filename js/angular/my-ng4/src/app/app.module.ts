import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AppComponent} from "./app.component";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";

import {TestServiceModule} from "./test-service/test-service.module";
import {TestComponentModule} from "./test-component/test-component.module";
import {TestPipeModule} from "./test-pipe/test-pipe.module";
import {TestDirectiveModule} from "./test-directive/test-directive.module";

import {AppRoutingModule} from "./app-routing.module";
import {AaaComponent} from "./test-route/aaa/aaa.component";

import {RouteReuseStrategy, Routes} from "@angular/router";
import {CustomReuseStrategy} from "./CustomReuseStrategy";

import {OAuthModule} from 'angular-oauth2-oidc';
import {FlexLayoutModule} from '@angular/flex-layout';
import 'hammerjs';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
// import { MatComponent } from './mat/mat.component';
// import { IonicApp, IonicModule } from 'ionic-angular';
import {DragulaModule} from 'ng2-dragula';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {MatToolbarModule} from '@angular/material/toolbar';
/*
该模块因为 re-export 了 BrowserModule， 所以不能放到 子路由中 lazy 加载。
 */
// import { OnsenModule } from 'ngx-onsenui';
// import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
//import {ClarityIcons}from "clarity-icons";

const routes: Routes = [
  {path: '', redirectTo: '/index.html', pathMatch: 'full'},
  {path: 'index.html', component: AaaComponent},
  //{path: 'aaa', loadChildren: './test-route/aaa/aaa.module#AaaModule'},
  //{path: 'bbb', loadChildren: './test-route/bbb/bbb.module#BbbModule'}
];
const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};

@NgModule({
  // schemas: [
  //   CUSTOM_ELEMENTS_SCHEMA,
  // ],

  imports: [
    PerfectScrollbarModule,
    MatIconModule,
    MatToolbarModule,
    MatSidenavModule,
    DragulaModule,
    BrowserModule,
    FormsModule,
    HttpModule,
    OAuthModule.forRoot(),
    FlexLayoutModule,
    BrowserAnimationsModule,

    TestServiceModule,
    TestComponentModule,
    TestPipeModule,
    TestDirectiveModule,

    // FIXME: 引入该包会造成 clarity 出错
    // OnsenModule,

    // RouterModule,
    AppRoutingModule,

    //RouterModule.forRoot(routes)
    //IonicModule.forRoot(AppComponent)
  ],
  declarations: [
    //MatComponent,
    AppComponent,
    AaaComponent  // router 默认显示的组件
  ],
  providers: [
    {provide: PERFECT_SCROLLBAR_CONFIG, useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG},
    {provide: RouteReuseStrategy, useClass: CustomReuseStrategy},
    {provide: LocationStrategy, useClass: HashLocationStrategy}
  ],
  bootstrap: [AppComponent]
  // bootstrap: [IonicApp]
})
export class AppModule {
}
