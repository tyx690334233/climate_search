import { Component, OnInit } from '@angular/core';
import { SearchService } from '../services/search.service';
import { FavoriteService } from '../services/favorite.service';


@Component({
  selector: 'app-res-tabs',
  templateUrl: './res-tabs.component.html',
  styleUrls: ['./res-tabs.component.css']
})
export class ResTabsComponent implements OnInit {
  tabs = [
    "nav-link active",
    "nav-link notActive",
    "nav-link notActive"
  ]
  starpic = "star_border";
  cityname;
  statename;
  stateSealUrl;
  tmpList;
  tempreture;
  summary;
  tweetHead = "https://twitter.com/intent/tweet?text=";
  tweetContent;
  style = false;
  isCityFav = false;

  constructor(private sSearch: SearchService, private fService: FavoriteService) {
    this.sSearch.getWeatherData().subscribe(data => {
      this.summary = data.weatherdata.currently.summary;
      this.tempreture = data.weatherdata.currently.temperature;
      this.statename = data.state;
      this.cityname = data.city;
      this.tweetContent = this.tweetHead + encodeURIComponent("The current temperature at " + this.cityname + " is " + this.tempreture + ". The weather conditions are " + this.summary) + "&hashtags=CSCI571WeatherSearch";
      console.log("trying to find is this fav city: " + this.cityname);
      this.isFav(this.cityname);
    })
    // this.sSearch.getState().subscribe(data => this.statename = data);
    this.sSearch.getStateSeal().subscribe(data => this.stateSealUrl = data);
  }

  ngOnInit() {


  }

  setActive($event): void {
    var setbutton = $event.toElement.innerHTML;
    switch (setbutton) {
      case "Current": {
        this.tabs[0] = "nav-link active";
        this.tabs[1] = "nav-link notActive";
        this.tabs[2] = "nav-link notActive";
        break;
      };
      case "Hourly": {
        this.tabs[0] = "nav-link notActive";
        this.tabs[1] = "nav-link active";
        this.tabs[2] = "nav-link notActive";
        break;
      };
      case "Weekly": {
        this.tabs[0] = "nav-link notActive";
        this.tabs[1] = "nav-link notActive";
        this.tabs[2] = "nav-link active";
        break;
      };
    }
  }
  toggleFav() {

    if (this.starpic == "star_border") {
      this.starpic = "star";
      this.style = true;
      this.addFavCity();
    }
    else {
      this.starpic = "star_border";
      this.style = false;
      this.removeFavCity(this.cityname);
    }
    
  }
  isFav(city) {
    let tmp = JSON.parse(localStorage.getItem("favList"));
    if (tmp == null) {

    }
    else {
      console.log(tmp.length);
      for (let item = 0; item < tmp.length; item++) {
        console.log(tmp[item].cityname);
        if (city == tmp[item].cityname) {
          this.isCityFav = true;
          // 
          this.style = true;
          this.starpic = "star";
          return;
        }
      }
      this.style = false;
      this.starpic = "star_border";
    }

  }

  addFavCity(){
    var listJson;
    this.tmpList = JSON.parse(localStorage.getItem("favList"));

    if (this.tmpList != null) {
      listJson = this.tmpList;
      listJson.push({
        cityname: this.cityname,
        statename: this.statename,
        sealUrl: this.stateSealUrl
      });
      localStorage.setItem("favList", JSON.stringify(listJson));
      this.fService.setFavList(listJson);
    }
    else {
      listJson = [];

      listJson.push({
        cityname: this.cityname,
        statename: this.statename,
        sealUrl: this.stateSealUrl
      });
      localStorage.setItem("favList", JSON.stringify(listJson));
      this.fService.setFavList(listJson);
    }
  }

  removeFavCity(city){
    var listJson;
    let tmp = JSON.parse(localStorage.getItem("favList"));

    if (tmp == null) {

    }
    else {
      console.log(tmp.length);
      for (let item = 0; item < tmp.length; item++) {
        console.log(tmp[item].cityname);
        if (city == tmp[item].cityname) {
          tmp.splice(item,1);
        }
      }
      localStorage.setItem("favList", JSON.stringify(tmp));
      this.fService.setFavList(tmp);
    }
  }
}
