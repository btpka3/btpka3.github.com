import {Component, OnInit} from "@angular/core";


@Component({
  ///selector: '[aaa-component]',
  templateUrl: './clarity.component.html',
  styleUrls: ['./clarity.component.css']
})
export class ClComponent implements OnInit {

  basic = false;
  modalOpened = false;

  constructor() {
  }

  ngOnInit(): void {

  }

  openModal(): void {
    this.modalOpened = !this.modalOpened;
  }


}
