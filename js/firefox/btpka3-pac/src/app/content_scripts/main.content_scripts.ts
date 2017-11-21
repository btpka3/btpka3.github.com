import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app.module';
import { environment } from '../../environments/environment';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.log(err));


console.log("------------------zll : " + new Date());


browser.runtime.onMessage.addListener(function(msg){
  console.log("Message from the background script:");
  console.log(msg);
  return Promise.resolve({response: "Hi from content script"});
});

