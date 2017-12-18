import {Component, OnInit} from "@angular/core";

import {MenuItem} from 'primeng/primeng';

@Component({
  templateUrl: './myPanel.component.html',
  styleUrls: ['./myPanel.component.scss']
})
export class MyPanelComponent implements OnInit {

  items: MenuItem[];
  items2: MenuItem[];

  constructor() {
  }

  ngOnInit(): void {

  }

}
