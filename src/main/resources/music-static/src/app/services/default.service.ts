import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Event, Router} from "@angular/router";
import {AuthService} from "./auth.service";

@Injectable()
export class DefaultService {
  private _url: string = "";
  private _lastUrl: string = null;
  private _secureRoutes: string[] = ['secure', 'home', 'profile', 'upload', 'search', 'settings'];
  private _insecureRoutes: string[] = ['insecure', 'signin', 'signup', 'active', ''];

  constructor() {
  }

  urlListener(event: Observable<Event>) {
    event.subscribe(e => {
      let values: string[] = e.url.split("/");
      if (values.length > 0) {
        this._lastUrl = values[1];
      } else {
        this._lastUrl = '';
      }
    });
  }

  get url(): string {
    return this._url;
  }

  get mock(): boolean {
    return false;
  }

  get lastUrl(): string {
    return this._lastUrl;
  }

  get secureRoutes(): string[] {
    return this._secureRoutes;
  }

  get insecureRoutes(): string[] {
    return this._insecureRoutes;
  }

  static  canActivateImpl(path: string, _router: Router, _authService: AuthService, _defaultService: DefaultService): Observable<boolean>|Promise<boolean>|boolean {
    let can: boolean = false;
    if (_defaultService.mock || path == null) can = true;
    if (_defaultService.secureRoutes.indexOf(path) != -1) {
      if (_authService.isAuth()) {
        can = true;
      }
      else {
        _router.navigate(['/', _defaultService.insecureRoutes[1]]);
        can = false;
      }
    }
    else if (_defaultService.insecureRoutes.indexOf(path) != -1) {
      if (_authService.isAuth()) {
        _router.navigate(['/', _defaultService.secureRoutes[1]]);
        can = false;
      }
      else {
        can = true;
      }
    }
    else {
      can = true;
    }
    return can;
  }
}
