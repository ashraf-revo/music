import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'm-error404',
  templateUrl: './error404.component.html',
  styleUrls: ['./error404.component.css']
})
export class Error404Component implements OnInit {
  isAuth: boolean = false;

  constructor(private _authService: AuthService) {

  }

  ngOnInit() {
    this.isAuth = this._authService.isAuth()
  }

}
