import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";

import {AppComponent} from "./app.component";

import {TestServiceModule} from "./test-service/test-service.module";
import {TestComponentModule} from "./test-component/test-component.module";
import {TestPipeModule} from "./test-pipe/test-pipe.module";
import {TestDirectiveModule} from "./test-directive/test-directive.module";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,

    TestServiceModule,
    TestComponentModule,
    TestPipeModule,
    TestDirectiveModule
  ],
  declarations: [
    AppComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
