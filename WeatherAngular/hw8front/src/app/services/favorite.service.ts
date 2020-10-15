import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FavoriteService {
  favList = new Subject();

  constructor() { }

  setFavList(data) {
    this.favList.next(data);
  }
  getFavList() {
    return this.favList.asObservable();
  }
}
