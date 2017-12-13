import {Component, OnDestroy, OnInit, ViewEncapsulation} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myNavbar.component.html',
  styleUrls: ['./myNavbar.component.scss'],
  //host: {class: 'myClass'}
  encapsulation: ViewEncapsulation.None
})
export class MyNavbarComponent implements OnInit, OnDestroy {

  time: Date;

  constructor() {
  }

  ngOnInit(): void {

  }

  ngOnDestroy(): void {

  }

  onSelect() {
    this.time = new Date();
  }

}
