import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  host: {class: 'myHome'}
})
export class HomeComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {

  }

}
