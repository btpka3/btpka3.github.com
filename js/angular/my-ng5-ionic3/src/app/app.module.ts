import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {APP_BASE_HREF} from "@angular/common";
import {IonicApp, IonicModule} from 'ionic-angular';

import {AppComponent} from './app.component';
import {HomePage} from '../pages/home/home.page';
import {UserPage} from '../pages/user/user.page';

@NgModule({
  declarations: [
    AppComponent,
    HomePage,
    UserPage
  ],
  entryComponents: [
    HomePage,
    UserPage
  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(AppComponent, {
      locationStrategy: "path"
    })
  ],
  providers: [
    {provide: APP_BASE_HREF, useValue: '/'}
  ],
  // bootstrap: [AppComponent]
  bootstrap: [IonicApp]
})
export class AppModule {
}
