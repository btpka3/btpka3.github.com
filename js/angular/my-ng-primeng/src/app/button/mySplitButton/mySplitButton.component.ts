import {Component, OnInit} from "@angular/core";

import {MenuItem} from 'primeng/primeng';

@Component({
  templateUrl: './mySplitButton.component.html',
  styleUrls: ['./mySplitButton.component.scss']
})
export class MySplitButtonComponent implements OnInit {

  items: MenuItem[] ;

  constructor() {

    this.items = [
      {
        label: 'Update1', icon: 'fa-refresh', command: () => {
        console.log("~~~11")
      }
      },
      {
        label: 'Delete', icon: 'fa-close', command: () => {
        console.log("~~~22")
      }
      },
      {label: 'Angular.io', icon: 'fa-link', url: 'http://angular.io'},
      {label: 'Theming', icon: 'fa-paint-brush', routerLink: ['/theming']}
    ];
  }

  ngOnInit(): void {
    // FIXME : this.items 在这里初始化不起作用。
  }
}
