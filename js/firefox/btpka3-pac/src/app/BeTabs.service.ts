import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subscription} from 'rxjs/Subscription';
import {Observer} from 'rxjs/Observer';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/publishReplay';
import {BeWebRequestService, ErrorDetail} from "./BeWebRequest.service";


export type ActiveInfo = {
  tabId: number,
  windowId: number
}


export type UpdateInfo = {
  tabId: number,
  changeInfo: ChangeInfo,
  tab: browser.tabs.Tab
}
export type ChangeInfo = {
  audible?: boolean,
  discarded?: boolean,
  favIconUrl?: string,
  mutedInfo?: browser.tabs.MutedInfo,
  pinned?: boolean,
  status?: string,
  title?: string,
  url?: string,
}


// ------------------- 标签页 active
@Injectable()
export class BeTabsService {

  // 当标签页被选中时（切换标签页）
  private activesListener: (activeInfo: ActiveInfo) => void;
  private activesObservable: Observable<ActiveInfo>;

  // 当标签页内容更新时（变更访问的URL）
  private updatesListener: (tabId: number, changeInfo: ChangeInfo, tab: browser.tabs.Tab) => void;
  private updatesObservable: Observable<ChangeInfo>;

  // 给定标签页出错的信息。
  private errorReqsListener: (tabId: number, changeInfo: ChangeInfo, tab: browser.tabs.Tab) => void;
  private errorReqsObservable: Observable<ErrorDetail>;


  private tabErrorReqsMap: Map<number, Subscription> = new Map();

  constructor(private beWebRequestService: BeWebRequestService) {

  }

  public setup(): void {
    this.setupActives();
    this.setupUpdates();
  }

  public destory(): void {
    if (this.activesListener) {
      browser.tabs.onActivated.removeListener(this.activesListener);
    }
    if (this.updatesListener) {
      browser.tabs.onUpdated.removeListener(this.updatesListener);
    }
  }

  private setupActives() {
    this.activesObservable = Observable.create((observer: Observer<ActiveInfo>) => {
      try {


        if (browser && browser.tabs && browser.tabs.onActivated) {
          this.activesListener = (activeInfo: ActiveInfo) => {
            observer.next(activeInfo);
          };

          browser.tabs.onActivated.addListener(this.activesListener);
        } else {
          observer.error("Can not find `browser.tabs.onActivated`");
        }
      } catch (err) {
        observer.error(err);
      }
    });
  }

  private setupUpdates() {
    this.updatesObservable = Observable.create((observer: Observer<UpdateInfo>) => {
      try {
        if (browser && browser.tabs && browser.tabs.onUpdated) {
          this.updatesListener = (tabId: number, changeInfo: ChangeInfo, tab: browser.tabs.Tab) => {
            observer.next({
              tabId,
              changeInfo,
              tab
            });
          };
          browser.tabs.onUpdated.addListener(this.updatesListener);
        } else {
          observer.error("Can not find `browser.tabs.onUpdated`");
        }
      } catch (err) {
        observer.error(err);
      }
    });
  }

  private setupErrorReqs() {
    this.updates.subscribe((activeInfo) => {

      let tabId: number = activeInfo.tabId;
      let x:Subscription;
      if (this.tabErrorReqsMap.has(tabId)) {
        x = this.tabErrorReqsMap.get(tabId);
      } else {
        x = this.beWebRequestService.errors.publishReplay()
          .filter((value,index) =>{
            return value.tabId ==  tabId;
          })

          .subscribe((errorDetail) => {

        });
        this.tabErrorReqsMap.set(tabId, x);
      }


    });
    this.errorReqsObservable = Observable.create((observer: Observer<ErrorDetail>) => {
      try {
        if (browser && browser.tabs && browser.tabs.onUpdated) {
          this.errorReqsListener = (tabId: number, changeInfo: ChangeInfo, tab: browser.tabs.Tab) => {
            observer.next({
              tabId,
              changeInfo,
              tab
            });
          };
          browser.tabs.onUpdated.addListener(this.errorReqsListener);
        } else {
          observer.error("Can not find `browser.tabs.onUpdated`");
        }
      } catch (err) {
        observer.error(err);
      }
    });
  }


  get actives(): Observable<ActiveInfo> {
    return this.activesObservable;
  }

  get updates(): Observable<ActiveInfo> {
    return this.activesObservable;
  }

  getTabFailedReq(tabId: number): Observable<ErrorDetail> {
    return this.tabErrorReqsMap.get(tabId);
  }
}


