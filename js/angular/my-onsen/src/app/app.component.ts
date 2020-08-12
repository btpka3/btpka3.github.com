import { Component } from '@angular/core';
import "node_modules/onsenui/css/onsenui.css"
import "node_modules/onsenui/css/onsen-css-components.css"

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.styl']
})
export class AppComponent {
  title = 'my-onsen';
   onClick() {
    //ons.notification.alert('Clicked!');
  }
}
