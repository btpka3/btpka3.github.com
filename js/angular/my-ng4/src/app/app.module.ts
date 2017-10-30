import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AppComponent} from "./app.component";

import {TestServiceModule} from "./test-service/test-service.module";
import {TestComponentModule} from "./test-component/test-component.module";
import {TestPipeModule} from "./test-pipe/test-pipe.module";
import {TestDirectiveModule} from "./test-directive/test-directive.module";

import {AppRoutingModule} from "./app-routing.module";
import {AaaComponent} from "./test-route/aaa/aaa.component";

import {Routes} from "@angular/router";

import { OAuthModule } from 'angular-oauth2-oidc';
import { FlexLayoutModule } from '@angular/flex-layout';
import 'hammerjs';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
// import { MatComponent } from './mat/mat.component';

const routes: Routes = [
  {path: '', redirectTo: '/index.html', pathMatch: 'full'},
  {path: 'index.html', component: AaaComponent},
  //{path: 'aaa', loadChildren: './test-route/aaa/aaa.module#AaaModule'},
  //{path: 'bbb', loadChildren: './test-route/bbb/bbb.module#BbbModule'}
];


@NgModule({
  imports: [
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

    // RouterModule,
    AppRoutingModule

    // RouterModule.forRoot(routes)
  ],
  declarations: [
    //MatComponent,
    AppComponent,
    AaaComponent  // router 默认显示的组件
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
