import {Component, OnInit} from "@angular/core";
import {MenuItem} from 'primeng/primeng';

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './primeng.component.html',
  styleUrls: ['./primeng.component.css']
})
export class PrimeNgComponent implements OnInit {
  items: MenuItem[];

  constructor() {
    this.items = [
      {
        label: 'Update', icon: 'fa-refresh', command: () => {
        console.log('111');
      }
      },
      {
        label: 'Delete', icon: 'fa-close', command: () => {
        console.log('222');
      }
      },
      {label: 'Angular.io', icon: 'fa-link', url: 'http://angular.io'},
      {label: 'Theming', icon: 'fa-paint-brush', routerLink: ['/bbb']}
    ];

  }

  ngOnInit(): void {

  }

}
