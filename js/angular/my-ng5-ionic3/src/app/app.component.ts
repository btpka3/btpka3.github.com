import {Component, OnInit} from '@angular/core';
import {HomePage} from '../pages/home/home.page';
// import {UserPage} from '../pages/user/user.page';
// import {NavController} from 'ionic-angular';
import {SwUpdate} from '@angular/service-worker';
import {environment} from '../environments/environment';

@Component({
  // selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  //template: `<ion-nav [root]="rootPage"></ion-nav>`
  host: {class: 'myClass'}
})
export class AppComponent implements OnInit {
  //@ViewChild('myNav') nav: NavController;

  title = 'app';
  rootPage = HomePage;

  constructor(private swUpdate: SwUpdate) {
  }

  ngOnInit() {
    // Let's navigate from TabsPage to Page1
    //this.nav.push(UserPage);

    if (environment.production) {
      this.swUpdate.available.subscribe(event => {
        console.log('A newer version is now available. Refresh the page now to update the cache');
      });
      this.swUpdate.checkForUpdate();
    }
  }

}
