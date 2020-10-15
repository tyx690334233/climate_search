import { Component, OnInit, Input } from '@angular/core';
import * as CanvasJS from '../../../assets/canvasjs.min';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { API } from '../../apilink';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'ngbd-modal-content',
  template: `
    <div class="modal-header" style="background:rgb(103 146 171)">
      <h4 class="modal-title">{{convertedTime}}</h4>
      <button type="button" class="close" aria-label="Close" (click)="activeModal.dismiss('Cross click')">
        <span aria-hidden="true">&times;</span>
      </button>
    </div>
    <div class="modal-body" style="background:rgb(156 209 240)">
      <div class="row">
        <div class="col-md-6 col-6">
          <span style="font-size: 2rem;">{{mod.city}}</span>
          <br>
          <span style="font-size: 2rem;">{{mod.tempreture}}<img style="width:0.5rem; vertical-align:top" src="https://cdn3.iconfinder.com/data/icons/virtual-notebook/16/button_shape_oval-512.png">F</span>
          <br>
          <span>{{mod.summary}}</span>
        </div>
        <div class="col-md-6 col-6">
          <img [src]="mod.imgsrc" style="width:8rem" >
        </div>
      </div>
      <hr>
      <div class="row">
        <div class="offset-md-6 col-md-6">
          <span>Precipition:{{mod.precip}}</span>
          <br>
          <span>Chance of Rain:{{mod.rain}}</span>
          <br>
          <span>Wind Speed:{{mod.windspeed}}</span>
          <br>
          <span>Humidity:{{mod.humi}}</span>
          <br>
          <span>Visibility:{{mod.visbi}}</span>
        </div>
      </div>
    </div>
  `
})
export class NgbdModalContent implements OnInit {
  apicollection = API;

  @Input() name;
  @Input() rawTime;
  @Input() convertedTime;
  @Input() lat;
  @Input() lon;
  mod = {
    city: "",
    tempreture: "",
    summary: "",
    imgsrc: "",
    precip: 1,
    rain: "",
    windspeed: "",
    humi: "",
    visbi: ""
  }


  constructor(public activeModal: NgbActiveModal, private http: HttpClient) { }
  ngOnInit() {
    let tmpapi = this.apicollection.getdetail + "lat=" + this.lat + "&lng=" + this.lon + "&time=" + this.rawTime;

    let response = this.http.get(tmpapi);
    response.subscribe(data => {
      console.log("this is from the modal");
      // console.log(data);
      this.setdata(data);
    });
  }

  setdata(data) {
    this.mod.city = sessionStorage.getItem("city");
    this.mod.tempreture = data.currently.temperature;
    this.mod.summary = data.currently.summary;
    this.mod.imgsrc = this.getDesImg(data.currently.icon);
    this.mod.precip = (Math.round(data.currently.precipIntensity * 100) / 100);
    this.mod.rain = (Math.round(data.currently.precipProbability * 10000)/100) + " %";
    this.mod.windspeed = Math.round(data.currently.windSpeed * 100) / 100 + " mph";
    this.mod.humi = data.currently.humidity * 100 + " %";
    this.mod.visbi = Math.round(data.currently.visibility) + " miles";
  }

  getDesImg(summary) {
    switch (summary) {
      case 'clear-day':
      case 'clear-night':
        return "https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png";
        break;
      case 'rain':
        return "https://cdn3.iconfinder.com/data/icons/weather-344/142/rain-512.png";
        break;
      case 'snow':
        return "https://cdn3.iconfinder.com/data/icons/weather-344/142/snow-512.png";
        break;
      case 'sleet':
        return "https://cdn3.iconfinder.com/data/icons/weather-344/142/lightning-512.png";
        break;
      case 'wind':
        return "https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_10-512.png";
        break;
      case 'fog':
        return "https://cdn3.iconfinder.com/data/icons/weather-344/142/cloudy-512.png";
        break;
      case 'cloudy':
        return "https://cdn3.iconfinder.com/data/icons/weather-344/142/cloud-512.png";
        break;
      case 'partly-cloudy-day':
      case 'partly-cloudy-night':
        return "https://cdn3.iconfinder.com/data/icons/weather-344/142/sunny-512.png";
        break;
      default:
        return "https://cdn3.iconfinder.com/data/icons/weather-344/142/sun-512.png";
        break;
    }
  }
}

@Component({
  selector: 'app-weekly-tab',
  templateUrl: './weekly-tab.component.html',
  styleUrls: ['./weekly-tab.component.css']
})

export class WeeklyTabComponent implements OnInit {
  temtable = [];
  rawtimetable = [];
  lat;
  lon;
  chart;
  constructor(private modalService: NgbModal) { }

  ngOnInit() {
    var tmp = sessionStorage.getItem("weather");

    this.setdata(JSON.parse(tmp));

    let chart = new CanvasJS.Chart("chartContainer", {
      animationEnabled: true,
      dataPointWidth: 15,
      title: {
        text: "Weekly Weather"
      },
      axisX: {
        title: "Days"
      },
      axisY: {
        includeZero: false,
        title: "Temperature in Fahrenheit",
        interval: 10,
        gridThickness: 0
      },
      legend: {
        verticalAlign: "top"
      },
      data: [{
        color: "#A6CFEE",
        type: "rangeBar",
        showInLegend: true,
        legendText: "Day wise temperature range",
        indexLabel:"{y[#index]}",
        toolTipContent: "<b>{label}</b>: {y[0]} to {y[1]}",
        click: (data => this.open(data)),
        dataPoints: this.temtable
      }],

    });
    console.log(this.temtable);
    chart.render();
  }

  setdata(data) {
    this.lat = data.latitude;
    this.lon = data.longitude;

    // let tmp = [0,0];
    let time;
    for (let i = 0; i < 7; i++) {
      let tmp = new Array(2);
      // this.temtable[i].x= i*10+10;
      tmp[0] = data.daily.data[i].temperatureLow;
      tmp[1] = data.daily.data[i].temperatureHigh;
      // console.log(tmp);
      // this.temtable[i].y = tmp;
      time = data.daily.data[i].time;
      // var s = new Date(time).toLocaleDateString("en-US");
      var s = new Date(time * 1000).toLocaleString("en-US", { timeZone: data.timezone }).split(',')[0];
      // this.temtable[i].label = time;
      this.rawtimetable.push({
        time: time
      })
      this.temtable.push({
        x: 70 - i * 10,
        y: tmp,
        label: s
      });

      // console.log(this.temtable);
    }
  }

  open(data) {
    // console.log(data);
    let tmpindex = data.dataPointIndex;

    const modalRef = this.modalService.open(NgbdModalContent);
    modalRef.componentInstance.name = 'World';
    modalRef.componentInstance.rawTime = this.rawtimetable[tmpindex].time;
    modalRef.componentInstance.convertedTime = this.temtable[tmpindex].label;
    modalRef.componentInstance.lat = this.lat;
    modalRef.componentInstance.lon = this.lon;
  }

}


