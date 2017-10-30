import {Component, OnInit} from "@angular/core";

@Component({
  selector: 'my-component',
  templateUrl: './my.component.html',
  styleUrls: [
    './my.component.css',
    './my.component1.scss'
  ]
})
export class MyComponent implements OnInit {
  myComponentResult = 'aabbccdd';

  constructor() {
  }

  ngOnInit(): void {

  }
}
