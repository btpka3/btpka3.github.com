import {Component, OnInit} from "@angular/core";

import {MenuItem} from 'primeng/primeng';

@Component({
  templateUrl: './myMenu.component.html',
  styleUrls: ['./myMenu.component.scss']
})
export class MyMenuComponent implements OnInit {

  items: MenuItem[];
  items2: MenuItem[];

  constructor() {
  }

  ngOnInit(): void {
    this.items = [
      {
        label: 'Update', icon: 'fa-refresh', command: () => {
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

    this.items2 = [{
      label: 'File',
      items: [
        {label: 'New', icon: 'fa-plus'},
        {label: 'Open', icon: 'fa-download'}
      ]
    },
      {
        label: 'Edit',
        items: [
          {label: 'Undo', icon: 'fa-refresh'},
          {label: 'Redo', icon: 'fa-repeat'}
        ]
      }];
  }


}
