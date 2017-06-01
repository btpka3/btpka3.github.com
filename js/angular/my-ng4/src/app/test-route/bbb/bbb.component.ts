import {Component, OnInit} from "@angular/core";

@Component({
  // selector: '[bbb-component]',
  templateUrl: './bbb.component.html',
  styleUrls: ['./bbb.component.css']
})
export class BbbComponent implements OnInit {
  v = 'b1-b2';

  constructor() {
  }

  ngOnInit(): void {

  }
}
