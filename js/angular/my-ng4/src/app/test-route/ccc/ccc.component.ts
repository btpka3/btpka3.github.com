import {Component, OnInit} from "@angular/core";

@Component({
  // selector: '[bbb-component]',
  templateUrl: './ccc.component.html',
  styleUrls: ['./ccc.component.css']
})
export class CccComponent implements OnInit {
  v = 'c1-c2';

  constructor() {
  }

  ngOnInit(): void {

  }
}
