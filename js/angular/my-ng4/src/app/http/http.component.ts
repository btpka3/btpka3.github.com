import {Component, OnInit} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";

import {HiResponse} from "./HiResponse";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './http.component.html',
  styleUrls: ['./http.component.css']
})
export class HttpComponent implements OnInit {

  results: Object;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {

  }

  getData(): void {
    console.log("-------OO");
    // Make the HTTP request:
    this.http
      .get<HiResponse>('http://localhost:3000', {
        headers: new HttpHeaders({
          "X-Requested-With": "XMLHttpRequest"
        }),
        observe: 'response'  // 有了该行，subscribe 的 next 函数将处理完整的 Http 响应对象
      })
      .subscribe(data => {
        // Read the result field from the JSON response.
        console.log('SUCCESS', data, data.body.a);
      }, err => {
        console.error('eeeeee', err);
      });
  }
}
