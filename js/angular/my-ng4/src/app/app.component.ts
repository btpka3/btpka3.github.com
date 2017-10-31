import {Component, OnInit} from "@angular/core";
import {MyService} from "./test-service/my.service";
import {JwksValidationHandler, OAuthService} from 'angular-oauth2-oidc';

import {authConfig} from './auth.config';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app works!';
  myServiceResult;
  myPipeSrc = "myPipeSrc";
  value: string = '10';

  constructor(public myService: MyService,
              private oauthService: OAuthService) {

    this.configureWithNewConfigApi();

  }

  private configureWithNewConfigApi() {
    this.oauthService.configure(authConfig);
    this.oauthService.tokenValidationHandler = new JwksValidationHandler();
    this.oauthService.loadDiscoveryDocumentAndTryLogin();
    this.oauthService.setupAutomaticSilentRefresh();

    this.oauthService.events.subscribe(e => {
      console.debug('oauth/oidc event', e);
    });

    this.oauthService.events.filter(e => e.type === 'session_terminated').subscribe(e => {
      console.debug('Your session has been terminated!');
    });

    this.oauthService.events.filter(e => e.type === 'token_received').subscribe(e => {
      // this.oauthService.loadUserProfile();
    });

  }

  public login() {
    this.oauthService.initImplicitFlow();
  }

  public logoff() {
    this.oauthService.logOut();
  }

  public showAt() {
    console.log("at = ", this.oauthService.getAccessToken());
  }

  public get name() {
    let claims = this.oauthService.getIdentityClaims();
    if (!claims) return null;
    return claims["given_name"]; // FIXME TS 编译错误，找不到属性
  }

  ngOnInit(): void {
    this.myServiceResult = "aaa : " + this.myService.add(1, 2)
  }
}
