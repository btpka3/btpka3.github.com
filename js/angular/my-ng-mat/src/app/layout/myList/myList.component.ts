import {Component, OnInit} from "@angular/core";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './myList.component.html',
  styleUrls: ['./myList.component.scss']
})
export class MyListComponent implements OnInit {


  links = [
    {title: "aaa", href: "https://www.baidu.com/s?wd=aaa"},
    {title: "bbb", href: "https://www.baidu.com/s?wd=bbb"},
    {title: "ccc", href: "https://www.baidu.com/s?wd=ccc"}
  ];

  constructor() {
  }

  ngOnInit(): void {

  }


}
