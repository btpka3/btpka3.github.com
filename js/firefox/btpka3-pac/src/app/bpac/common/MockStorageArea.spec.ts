import {async} from '@angular/core/testing';
// import { RouterTestingModule } from '@angular/router/testing';
import {MockStorageArea} from './MockStorageArea';

describe('MockStorageArea', () => {
  beforeEach(async(() => {
    // TestBed.configureTestingModule({
    //   imports: [
    //     RouterTestingModule
    //   ],
    //   declarations: [
    //     AppComponent
    //   ],
    // }).compileComponents();
  }));


  it('should create the app', async () => {

    let sa: browser.storage.StorageArea = new MockStorageArea();

    let a: browser.storage.StorageObject = await sa.get("a");
    expect(a).toBeDefined()

    //
    // const fixture = TestBed.createComponent(AppComponent);
    // const app = fixture.debugElement.componentInstance;
    // expect(app).toBeTruthy();
  });

  // it(`should have as title 'app'`, async(() => {
  //   const fixture = TestBed.createComponent(AppComponent);
  //   const app = fixture.debugElement.componentInstance;
  //   expect(app.title).toEqual('app');
  // }));
  // it('should render title in a h1 tag', async(() => {
  //   const fixture = TestBed.createComponent(AppComponent);
  //   fixture.detectChanges();
  //   const compiled = fixture.debugElement.nativeElement;
  //   expect(compiled.querySelector('h1').textContent).toContain('Welcome to app!');
  // }));
});
