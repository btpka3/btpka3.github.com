import {Component} from "@angular/core";

@Component({
  moduleId: module.id,
  selector: 'my-app',
  styleUrls: ['app.component.css'],
  template: `
    <h1>{{title}}</h1>
    <div class="header-bar"></div>

    <nav>
      <a routerLink="/dashboard">Dashboard</a>
      <a routerLink="/heroes">Heroes</a>
    </nav>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {
  title = 'Tour of Heroes';
}
