import {Component, OnInit} from "@angular/core";

@Component({
  // selector: '[bbb-component]',
  templateUrl: './ccc.component.html',
  styleUrls: ['./ccc.component.css']
})
export class CccComponent implements OnInit {
  v = 'b1-b2';

  constructor() {
  }

  ngOnInit(): void {

  }
}
