import { Component } from '@angular/core';
import { HomePage } from '../pages/home.page';
@Component({
  // selector: 'app-root',
  // templateUrl: './app.component.html',
  // styleUrls: ['./app.component.scss']
  template: `<ion-nav [root]="rootPage"></ion-nav>`
})
export class AppComponent {
  title = 'app';
  rootPage = HomePage;
}
