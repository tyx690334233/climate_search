import { Component, OnInit } from '@angular/core';
import { SearchService } from '../../services/search.service';
import { HttpParams, HttpClient } from '@angular/common/http';
import { API } from '../../apilink';

@Component({
  selector: 'app-current-tab',
  templateUrl: './current-tab.component.html',
  styleUrls: ['./current-tab.component.css']
})
export class CurrentTabComponent implements OnInit {
  weatherData: any;
  city: any;
  state: any;
  statejson: any;

  current = {
    location: "",
    timezone: "",
    imgsrc: "",
    curTem: "",
    summary: "",
    humidity: "",
    pressure: "",
    windspeed: "",
    visibility: "",
    cloudcover: "",
    ozone: ""
  }
  iconPic = [
    { key: "humidity", value: "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-16-512.png" },
    { key: "pressure", value: "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-25-512.png" },
    { key: "windspeed", value: "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-27-512.png" },
    { key: "visibility", value: "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-30-512.png" },
    { key: "cloudcover", value: "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-28-512.png" },
    { key: "ozone", value: "https://cdn2.iconfinder.com/data/icons/weather-74/24/weather-24-512.png" }
  ]

  Temperature = "https://cdn3.iconfinder.com/data/icons/virtualnotebook/16/button_shape_oval-512.png";
  isShowData = false;
  apicollection = API;
  progressStatus = {
    progress: 0,
    isshow: false
  }

  constructor(private sService: SearchService, private http: HttpClient) {
    this.sService.getWeatherData().subscribe(data => {
      console.log("i change because i am here");
      this.weatherData = data.weatherdata;
      // console.log(this.weatherData);
      this.setDisplayData();
      this.isShowData = true;
      this.city = data.city;
      sessionStorage.setItem("city", this.city);
      this.state = data.state;
      let param = new HttpParams()
        .set("state", this.state);
      console.log("i am finding the img of " + this.state);
      let response = this.http.get(this.apicollection.getimage, { params: param });

      response.subscribe(data => {
        this.statejson = data;
        console.log("i am finding the img of " + this.state);
        this.current.imgsrc = this.statejson.items[0].link;
        sessionStorage.setItem("customUrl", this.current.imgsrc);
        this.sService.setStateSeal(this.current.imgsrc);
        if (this.weatherData != null && this.current.imgsrc.length != 0) {
          this.progressStatus.progress = 80;
          this.progressStatus.isshow = false;
          this.sService.setProgress(this.progressStatus);
          this.sService.setResultShow(true);
        }
      })
      this.current.imgsrc="";
    })
    // this.sService.getState().subscribe(data => {
    //   this.state = data;
    //   let param = new HttpParams()
    //     .set("state", this.state);
    //   console.log("i am finding the img of " + this.state);
    //   let response = this.http.get(this.apicollection.getimage, { params: param });

    //   response.subscribe(data => {
    //     this.statejson = data;
    //     this.current.imgsrc = this.statejson.items[0].link;
    //     sessionStorage.setItem("customUrl", this.current.imgsrc);
    //     this.sService.setStateSeal(this.current.imgsrc);
    //   })
    //   if (this.weatherData != null && this.current.imgsrc.length != 0) {
    //     this.progressStatus.progress = 80;
    //     this.progressStatus.isshow = false;
    //     this.sService.setProgress(this.progressStatus);
    //     this.sService.setResultShow(true);
    //   }
    // });
  }

  ngOnInit() {
    this.loadData();
    this.isShowData = true;
  }


  getdata() {
    // this.city = this.sService.getCity().subscribe(
    //   data=>{
    //     // this.current.location = data;
    //   }
    // )
    //  this.sService.weatherInfo.subscribe(
    //     data=>{
    //       console.log("i change because i am here");
    //       this.weatherData = data;
    //       this.current.timezone = data["timezone"];
    //       this.current.curTem = data["currently"]["temperature"];
    //       this.current.summary = data["currently"]["summary"];
    //       this.isShowData = true;
    //       localStorage.setItem("weather", data);
    //       console.log(this.city);
    //       console.log(this.state);
    //     }
    //   );
  }
  loadData() {
    // var tmp = localStorage.getItem("weather");

    var tmp = sessionStorage.getItem("weather");

    this.weatherData = JSON.parse(tmp);
    // console.log(this.weatherData);
    this.setDisplayData();
    // tmp = JSON.parse(localStorage.getItem("city"));
    // this.city = tmp;
    this.city = sessionStorage.getItem("city");


    this.current.imgsrc = sessionStorage.getItem("customUrl");
  }

  setDisplayData() {
    this.current.timezone = this.weatherData.timezone;
    this.current.curTem = this.weatherData.currently.temperature;
    this.current.summary = this.weatherData.currently.summary;
    this.current.humidity = this.weatherData.currently.humidity;
    this.current.pressure = this.weatherData.currently.pressure;
    this.current.windspeed = this.weatherData.currently.windSpeed;
    this.current.visibility = this.weatherData.currently.visibility;
    this.current.cloudcover = this.weatherData.currently.cloudCover;
    this.current.ozone = this.weatherData.currently.ozone;
  }

  isAvailable(key: string): boolean {

    var index = key.toLowerCase();
    if (this.current[index] != "") {
      return true;
    }
    return false;
  }

}
