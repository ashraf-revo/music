import {Component, OnInit, OnDestroy} from "@angular/core";
import {PlayerService} from "../../../services/player.service";
import {Song} from "../../../domain/Song";
import {SongService} from "../../../services/song.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'm-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit,OnDestroy {
  songs: Song[] = [];
  private subscription: Subscription;

  constructor(private _playerService: PlayerService, private _songService: SongService) {
  }

  ngOnInit() {
    this.subscription = this._songService.songs().subscribe(songs => this.songs = songs);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe()
  }

  play(song: Song) {
    song.from = this.songs;
    this._playerService.setPlayer(song)
  }
}
