import {Injectable} from "@angular/core";


export interface AbstractProxy {
  id: string;
  category: ProxyCategoryEnum,
  name?: string,
  disabled?: boolean
}

export interface SimpleProxy extends AbstractProxy {
  category: ProxyCategoryEnum.SIMPLE,
  type: PacProxyTypeEnum,
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
  SIMPLE="SIMPLE",
  COMBINE="COMBINE",
  DYNAMIC="DYNAMIC"
}

export enum SimpleProxyTyp1eEnum {
  DIRECT = "DIRECT",
  PROXY = "PROXY",
  SOCKS = "SOCKS",
  HTTP = "HTTP",
  HTTPS = "HTTPS",
  SOCKS4 = "SOCKS4",
  SOCKS5 = "SOCKS5"
}


export enum PacProxyTypeEnum {
  HTTP = "http",
  HTTPS = "https",
  SOCKS4 = "socks4",
  SOCKS = "socks",
  DIRECT = "direct"
}

export type PacProxy = {
  type: PacProxyTypeEnum,
  host?: string,
  port?: number,
  username?: string,
  password?: string,
  proxyDNS?: boolean,
  failoverTimeout?: number
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
