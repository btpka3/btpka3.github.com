import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Observer} from 'rxjs/Observer';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';


export type ProxyInfo = {

  host: string,
  port: number,
  type: "http" | "https" | "socks" | "socks4" | "direct" | "unknown",
  username: string,
  proxyDNS: string,
  failoverTimeout: number
}


export type ErrorDetail = {
  error: string,
  frameId: number,
  fromCache: boolean,
  ip?: string,
  method: string,
  originUrl: string,
  parentFrameId: number,
  proxyInfo: ProxyInfo,
  requestId: string,
  tabId: number,
  timeStamp: number,
  type: browser.webRequest.ResourceType,
  url: string
}


// ---------------------- 订阅 连接失败的信息 （全部失败，部分失败）
@Injectable()
export class BeWebRequestService {

  private errorsListener: (errorDetail: ErrorDetail) => void;
  private errorsObservable: Observable<ErrorDetail>;


  constructor() {

  }

  public setup():void{
    this.setupErrors();
  }

  public destory(): void {
    this.tearDownErrors();
  }


  private setupErrors() {
    this.errorsObservable = Observable.create((observer: Observer<ErrorDetail>) => {
      try {
        if (browser && browser.webRequest && browser.webRequest.onErrorOccurred) {
          this.errorsListener = (errorDetail: ErrorDetail) => {
            observer.next(errorDetail);
          };
          browser.webRequest.onErrorOccurred.addListener(this.errorsListener, {
            urls: ["<all_urls>"]
          });
        } else {
          observer.error("Can not find `browser.webRequest.onErrorOccurred`");
        }
      } catch (err) {
        observer.error(err);
      }
    });
  }

  private tearDownErrors(): void {
    if (this.errorsListener) {
      browser.webRequest.onErrorOccurred.removeListener(this.errorsListener);
    }
  }

  get errors(): Observable<ErrorDetail> {
    return this.errorsObservable;
  }

}


/*
当 tab 更新时，重新订阅该 tab 所有的 WebRequest 的错误请求。
- 排除 fromCache = true 的
- 排除 proxyInfo 有值的
- 按域名进行分组统计
*/
