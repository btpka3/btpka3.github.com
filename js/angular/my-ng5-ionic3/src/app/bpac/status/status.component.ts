import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss'],
  host: {class: 'zmain'}
})
export class BpacStatusComponent implements OnInit {
  myDate: Date = new Date();
  checked: boolean;

  constructor() {
  }

  ngOnInit(): void {

  }

  c(): void {
    this.checked = !this.checked;
  }

}
