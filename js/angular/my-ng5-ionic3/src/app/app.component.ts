import { Component,ViewChild } from '@angular/core';
import { HomePage } from '../pages/home/home.page';
import { UserPage } from '../pages/user/user.page';
import { NavController } from 'ionic-angular';
@Component({
  // selector: 'app-root',
   templateUrl: './app.component.html',
  // styleUrls: ['./app.component.scss']
  //template: `<ion-nav [root]="rootPage"></ion-nav>`
})
export class AppComponent {
  @ViewChild('myNav') nav: NavController;

  title = 'app';
  rootPage = HomePage;

  ngOnInit() {
    // Let's navigate from TabsPage to Page1
    this.nav.push(UserPage);
  }

}
