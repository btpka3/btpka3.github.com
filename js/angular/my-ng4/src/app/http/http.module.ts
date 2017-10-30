import {NgModule} from "@angular/core";
import {HttpComponent} from "./http.component";
import {HttpRoutingModule} from "./http-routing.module";
import {HttpClientModule} from '@angular/common/http';
//import {BrowserModule} from '@angular/platform-browser';


/*
# 需要先启动模拟式服务器
node src/test/mock-server.js
 */
@NgModule({
  imports: [
    //  BrowserModule,
    HttpRoutingModule,
    HttpClientModule
  ],
  declarations: [HttpComponent]
})
export class HttpModule {
}
