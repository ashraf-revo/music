import {Component, OnInit, Input} from "@angular/core";
import {Router} from "@angular/router";
import {AuthService} from "../../../services/auth.service";
import {User} from "../../../domain/User";
import {PlayerService} from "../../../services/player.service";
import {UserService} from "../../../services/user.service";

@Component({
  selector: 'm-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  @Input()
  searchKey: string = '';
  user: User = new User();

  constructor(private _router: Router, private _authService: AuthService, private _playerService: PlayerService, private _userService: UserService) {
  }

  ngOnInit() {
    this.user = this._authService.getAuth();
  }

  search() {
    if (this.searchKey.trim() != "") {
      let s = this.searchKey.trim().split(" ").join("-");
      this._router.navigate(['/search', s]);
    }
  }

  logout() {
    this._playerService.stop();
    this._userService.logout();
  }
}
