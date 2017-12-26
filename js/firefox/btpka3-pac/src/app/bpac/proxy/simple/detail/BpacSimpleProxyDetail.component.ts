import {
  AfterViewInit,
  Component,
  OnChanges,
  OnDestroy,
  OnInit,
  QueryList,
  SimpleChanges,
  ViewChild,
  ViewChildren
} from "@angular/core";
import {Location} from '@angular/common';
import {ActivatedRoute, Event, NavigationStart, Router} from '@angular/router';
import {NgForm} from '@angular/forms';

import {ProxyCategoryEnum, SimpleProxy, SimpleProxyTyp1eEnum,} from "../../../common/ProxyStoreService";

@Component({
  ///selector: '[aaa-component]',
  templateUrl: './BpacSimpleProxyDetail.component.html',
  styleUrls: ['./BpacSimpleProxyDetail.component.scss']
})
export class BpacSimpleProxyDetailComponent implements OnInit, OnChanges, AfterViewInit, OnDestroy {


  @ViewChild(NgForm)
  f: NgForm;

  @ViewChildren("f")
  formList: QueryList<NgForm>;


  ngOnChanges(changes: SimpleChanges): void {
    console.log("=======change :", changes);
  }


  typeOptions = [
    //{value: SimpleProxyTyp1eEnum.DIRECT, viewValue: 'DIRECT'},
    {value: SimpleProxyTyp1eEnum.PROXY, viewValue: 'PROXY'},
    {value: SimpleProxyTyp1eEnum.SOCKS, viewValue: 'SOCKS'},
    {value: SimpleProxyTyp1eEnum.HTTP, viewValue: 'HTTP'},
    {value: SimpleProxyTyp1eEnum.HTTPS, viewValue: 'HTTPS'},
    {value: SimpleProxyTyp1eEnum.SOCKS4, viewValue: 'SOCKS4'},
    {value: SimpleProxyTyp1eEnum.SOCKS5, viewValue: 'SOCKS5'},
  ];


  simpleProxy: SimpleProxy;
  id: string;

  constructor(private location: Location,
              private router: Router,
              private route: ActivatedRoute) {
    this.router.events.subscribe((event: Event) => {

      // 从当前状态迁移到别的路径
      if (event instanceof NavigationStart) {
        console.log("constructor : NavigationStart")
      }
      // NavigationEnd
      // NavigationCancel
      // NavigationError
      // RoutesRecognized
    });
  }

  ngOnInit(): void {

    console.log("ngOnInit : this.formList = ", this.formList);
    console.log("ngOnInit : this.f = ", this.f);
    // 该版本使通过 Observable 监听变化的，能反应URL上的实时变化
    this.route.params.subscribe((params: { id: string }) => {
      this.id = params.id;
      //browser.storage.local.get("proxies");
      this.simpleProxy = {
        id: params.id,
        category: ProxyCategoryEnum.SIMPLE,
        type: SimpleProxyTyp1eEnum.SOCKS5,
        host: null,
        port: null
      };
    });
  }

  ngAfterViewInit(): void {
    console.log("ngAfterViewInit : this.formList = ", this.formList);
    console.log("ngAfterViewInit : this.f = ", this.f);


    // 这里的频次太高了
    this.f.valueChanges.subscribe((c) => {
      //console.log("form changes : ", c);
    })
  }

  logSimpleProxy(): void {
    console.log(this.simpleProxy);
  }


  ngOnDestroy(): void {
    // TODO 将表单提交保存
  }
}
