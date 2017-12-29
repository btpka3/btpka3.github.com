import {Component, OnInit} from '@angular/core';
import {SwUpdate} from '@angular/service-worker';


import {environment} from '../environments/environment';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  host: {class: 'myClass'}
})
export class AppComponent implements OnInit {
  title = 'app';

  constructor(private swUpdate: SwUpdate) {
  }

  ngOnInit(): void {

    if (environment.production) {
      this.swUpdate.available.subscribe(event => {
        console.log('A newer version is now available. Refresh the page now to update the cache');
      });
      this.swUpdate.checkForUpdate();
    }
  }
}
