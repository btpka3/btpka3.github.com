import {ActivatedRouteSnapshot, DetachedRouteHandle, RouteReuseStrategy} from '@angular/router';


// https://www.softwarearchitekt.at/post/2016/12/02/sticky-routes-in-angular-2-3-with-routereusestrategy.aspx

export class CustomReuseStrategy implements RouteReuseStrategy {

  handlers: { [key: string]: DetachedRouteHandle } = {};

  shouldDetach(route: ActivatedRouteSnapshot): boolean {
    console.debug('CustomReuseStrategy:shouldDetach', route);
    return true;
  }

  store(route: ActivatedRouteSnapshot, handle: DetachedRouteHandle): void {
    let p = this.getFullPath(route);
    console.debug('CustomReuseStrategy:store', p, route, handle);
    //this.handlers[route.routeConfig.path] = handle;
    this.handlers[p] = handle;
  }

  shouldAttach(route: ActivatedRouteSnapshot): boolean {
    let p = this.getFullPath(route);
    //let rtn = !!route.routeConfig && !!this.handlers[route.routeConfig.path];
    let rtn = !!route.routeConfig && !!this.handlers[p];
    console.debug('CustomReuseStrategy:shouldAttach', p, rtn, route);
    return rtn;
  }

  retrieve(route: ActivatedRouteSnapshot): DetachedRouteHandle {
    let p = this.getFullPath(route);
    console.debug('CustomReuseStrategy:retrieve', p, route);
    if (!route.routeConfig) return null;
    //return this.handlers[route.routeConfig.path];
    return this.handlers[p];
  }

  shouldReuseRoute(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
    let rtn = future.routeConfig === curr.routeConfig;
    console.debug('CustomReuseStrategy:shouldReuseRoute', rtn, future, curr);
    return rtn;
  }

  // FIXME : 没有考虑参数
  getFullPath(route: ActivatedRouteSnapshot): string {
    let rootPath = null;

    if (route.parent) {
      rootPath = this.getFullPath(route.parent)
    }

    if (route.routeConfig) {
      return rootPath + route.routeConfig.path;
    } else {
      return null;
    }
  }

}
