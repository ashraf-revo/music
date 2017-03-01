import {Injectable} from "@angular/core";
import {CanActivate, RouterStateSnapshot, ActivatedRouteSnapshot, Router} from "@angular/router";
import {Observable} from "rxjs";
import {AuthService} from "./auth.service";
import {DefaultService} from "./default.service";

@Injectable()
export class InSecureGuardService implements CanActivate {

  constructor(private _router: Router, private _authService: AuthService, private _defaultService: DefaultService) {

  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean>|Promise<boolean>|boolean {
    return DefaultService.canActivateImpl(this._defaultService.insecureRoutes[0], this._router, this._authService, this._defaultService);
  }
}
