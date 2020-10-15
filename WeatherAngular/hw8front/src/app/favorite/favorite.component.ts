import { Component, OnInit } from '@angular/core';
import { FavoriteService } from '../services/favorite.service';
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-favorite',
  templateUrl: './favorite.component.html',
  styleUrls: ['./favorite.component.css']
})
export class FavoriteComponent implements OnInit {

  tmpList;
  CityArray;
  isShow = false;
  constructor(private fService: FavoriteService, private sService: SearchService) {
    this.fService.getFavList().subscribe(data => {
      this.CityArray = data;
      // console.log(this.CityArray);
      if (this.CityArray.length > 0) {
        this.isShow = true;
      } else {
        this.isShow = false;
      }
    })
  }

  ngOnInit() {
    this.tmpList = JSON.parse(localStorage.getItem("favList"));
    if (this.tmpList == null || this.tmpList.length == 0) {
      this.isShow = false;
    }
    else {
      this.isShow = true;
      this.CityArray = this.tmpList;
      console.log(this.CityArray);
    }
    console.log(this.isShow);
  }

  deleteFav(a) {
    console.log(a);
    // delete one element
    this.CityArray.splice(a, 1);
    this.fService.setFavList(this.CityArray);
    localStorage.setItem("favList", JSON.stringify(this.CityArray));
  }

  showres(a) {
    this.sService.setResultShow(false);
    this.sService.setFavShow(false);
    let tmpform =this.CityArray[a];
    console.log("i want to resubmit the form");
    console.log(tmpform);
    this.sService.setResubmit(JSON.stringify(tmpform));
  }
}
