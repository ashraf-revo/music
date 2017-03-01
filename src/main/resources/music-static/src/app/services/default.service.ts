import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Event, NavigationStart} from "@angular/router";

@Injectable()
export class DefaultService {
  private _url: string = "";
  private _lastUrl: string = null;

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
}
