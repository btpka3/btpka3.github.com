import {Injectable} from "@angular/core";


export interface AbstractProxy {
  id: string;
  category: ProxyCategoryEnum,
  name?: string,
  disabled?: boolean
}

export interface SimpleProxy extends AbstractProxy {
  category: ProxyCategoryEnum.SIMPLE,
  type: SimpleProxyTyp1eEnum,
  host: string,
  port: number,
  proxyDNS?: boolean,
  username?: string,
  password?: string,
  timeout?: number,
}

export interface CombineProxy extends AbstractProxy {
  category: ProxyCategoryEnum.COMBINE,
  simpleProxyIds: string[],
}

export interface DynamicProxy extends AbstractProxy {
  category: ProxyCategoryEnum.DYNAMIC,
  items: DynamicProxyItem[],
}

export interface DynamicProxyItem {
  pattern: string,
  proxyIds: string[]
}

export enum ProxyCategoryEnum {
  SIMPLE,
  COMBINE,
  DYNAMIC
}

export enum SimpleProxyTyp1eEnum {
  DIRECT,
  PROXY,
  SOCKS,
  HTTP,
  HTTPS,
  SOCKS4,
  SOCKS5
}

@Injectable()
export class ProxyStoreService {


  async save(proxy: AbstractProxy): Promise<void> {
    let proxiesObj: { proxies: AbstractProxy[] } = await browser.storage.local.get({"proxies": []});
    let proxies: AbstractProxy[] = proxiesObj.proxies;

    let oldProxy = null;

    proxy


    return null;
  }

}
