import {BrowserModule} from '@angular/platform-browser';
import {ErrorHandler, NgModule} from '@angular/core';
import {IonicApp, IonicErrorHandler, IonicModule} from 'ionic-angular';
import {Network} from '@ionic-native/network';
import {StatusBar} from '@ionic-native/status-bar';
import {SplashScreen} from '@ionic-native/splash-screen';
import {FormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ServiceWorkerModule} from '@angular/service-worker';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MatSidenavModule} from '@angular/material/sidenav';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {AaaComponent} from "./aaa/aaa.component";


import {BpacStatusComponent} from "./bpac/status/status.component";


import {HomePage} from "../pages/home/home.page";
import {UserPage} from "../pages/user/user.page";
import {environment} from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    AaaComponent,

    BpacStatusComponent,

    HomePage,
    UserPage,
  ],
  entryComponents: [
    AppComponent
    //HomePage,
    //UserPage
  ],

  imports: [
    FlexLayoutModule,
    MatSidenavModule,
    BrowserModule,
    ServiceWorkerModule.register('/ngsw-worker.js', {enabled: environment.production}),

    // IonicModule.forRoot(AppComponent, {
    //     //locationStrategy: "path"
    //   },
    //   {
    //     links: [
    //       {component: HomePage, segment: "home"},
    //       {component: UserPage, segment: "user"}
    //     ]
    //   })

    AppRoutingModule,
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule,

    IonicModule.forRoot(AppComponent),
    IonicModule
  ],
  providers: [
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    // {provide: APP_BASE_HREF, useValue: '/'},


    Network,
    StatusBar,
    SplashScreen,

  ],
  // bootstrap: [AppComponent]
  bootstrap: [IonicApp]
})
export class AppModule {
}
