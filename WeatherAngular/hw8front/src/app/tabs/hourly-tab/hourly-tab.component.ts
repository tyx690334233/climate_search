import { Component, OnInit } from '@angular/core';
import { DetailService } from '../../services/detail.service';

@Component({
  selector: 'app-hourly-tab',
  templateUrl: './hourly-tab.component.html',
  styleUrls: ['./hourly-tab.component.css']
})
export class HourlyTabComponent implements OnInit {

  hourlyOption = [
    { key: "temperature", value: "Temperature" },
    { key: "pressure", value: "Pressure" },
    { key: "humidity", value: "Humidity" },
    { key: "ozone", value: "Ozone" },
    { key: "visibility", value: "Visibility" },
    { key: "windspeed", value: "Wind Speed" }
  ]
  currentoption: string = "temperature";
  dataSize = 24;
  temperatureCol: number[] = new Array(this.dataSize);
  pressureCol: number[] = new Array(this.dataSize);
  humidityCol: number[] = new Array(this.dataSize);
  ozoneCol: number[] = new Array(this.dataSize);
  visibiCol: number[] = new Array(this.dataSize);
  windSpeedCol: number[] = new Array(this.dataSize);

  curMin;
  curMax;
  constructor(private dService: DetailService) {
    // this.dService.getdetailweather().subscribe(data => {
    //   console.log("ihere is for hourly tab");
    //   console.log(data);
    //   this.setdataCol(data);
    // })
  }

  ngOnInit() {
    // this.dService.loadData();
    var tmp = sessionStorage.getItem("weather");
    this.setdataCol(JSON.parse(tmp));
  }

  public barChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
    legend: {
      onClick: (e, legendItem) => { }
    },
    scaleLabel: {
      padding: 10
    },
    scales: {
      yAxes: [{
        ticks: {
          suggestedMin: this.curMin,
          suggestedMax: this.curMax
        }
      }]
    }
  };
  public barChartLabels = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23'];
  public barChartType = 'bar';
  public barChartLegend = true;
  public barChartData = [
    {
      data:
        this.temperatureCol,
      label:
        this.currentoption,
      backgroundColor:
        'rgb(166, 207, 238)',
      hoverBackgroundColor:
        'rgb(93, 134, 163)',
      borderColor:
        'rgb(166, 207, 238)'
    }
  ];

  setdisplayoption(curopt) {
    console.log(curopt);
    this.currentoption = curopt;
    switch (curopt) {
      case "temperature":
        this.updateChart(this.currentoption, this.temperatureCol);
        break;
      case "pressure":
        this.updateChart(this.currentoption, this.pressureCol);
        break;
      case "humidity":
        this.updateChart(this.currentoption, this.humidityCol);
        break;
      case "ozone":
        this.updateChart(this.currentoption, this.ozoneCol);
        break;
      case "visibility":
        this.updateChart(this.currentoption, this.visibiCol);
        break;
      case "windspeed":
        this.updateChart(this.currentoption, this.windSpeedCol);
        break;
    }
  }

  updateChart(label: any, data?: any) {

    let min = 5000;
    let max = 0;
    for (let a in data) {
      let b = data[a];
      min = (min < b) ? min : b;
      max = (max < b) ? b : max;
    }
    this.curMin = min - (max - min) / 3;
    this.curMax = max + (max - min) / 3;
    this.barChartData[0].data = data;
    this.barChartData[0].label = label;
    this.barChartOptions = {
      scaleShowVerticalLines: false,
      responsive: true,
      legend: {
        onClick: (e, legendItem) => { }
      },
      scaleLabel: {
        padding: 10
      },
      scales: {
        yAxes: [{
          ticks: {
            suggestedMin: this.curMin,
            suggestedMax: this.curMax
          }
        }]
      }
    };

  }

setdataCol(data) {
  console.log(data);
  let tmp = data.hourly.data;

  for (let i = 0; i < this.dataSize; i++) {
    this.temperatureCol[i] = tmp[i].temperature;
    this.pressureCol[i] = tmp[i].pressure;
    this.humidityCol[i] = tmp[i].humidity;
    this.ozoneCol[i] = tmp[i].ozone;
    this.visibiCol[i] = tmp[i].visibility;
    this.windSpeedCol[i] = tmp[i].windSpeed;
  }
}

niceNum(localRange: any, round: any) {
  var exponent; /** exponent of localRange */
  var fraction; /** fractional part of localRange */
  var niceFraction; /** nice, rounded fraction */

  exponent = Math.floor(Math.log10(localRange));
  fraction = localRange / Math.pow(10, exponent);

  if (round) {
    if (fraction < 1.5)
      niceFraction = 1;
    else if (fraction < 3)
      niceFraction = 2;
    else if (fraction < 7)
      niceFraction = 5;
    else
      niceFraction = 10;
  } else {
    if (fraction <= 1)
      niceFraction = 1;
    else if (fraction <= 2)
      niceFraction = 2;
    else if (fraction <= 5)
      niceFraction = 5;
    else
      niceFraction = 10;
  }
  return niceFraction * Math.pow(10, exponent);
}
}
