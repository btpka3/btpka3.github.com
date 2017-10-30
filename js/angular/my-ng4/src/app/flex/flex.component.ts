import {Component, OnInit} from "@angular/core";
import {HttpClient} from "@angular/common/http";


@Component({
  ///selector: '[aaa-component]',
  templateUrl: './flex.component.html',
  styleUrls: ['./flex.component.css']
})
export class FlexComponent implements OnInit {


  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {

  }

}
