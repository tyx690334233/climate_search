import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { SearchService } from './search.service';
import { API } from '../apilink';
import { HttpParams, HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DetailService {
  apicollection = API;

  detailLocation = new Subject();
  curtimeSubject = new Subject();
  detailweatherinfo = new Subject();

  curtime: string;
  curlat: string;
  curlng: string;

  detailinfoSubject = new Subject();

  errorMSG = new Subject();
  clear = new Subject();

  constructor(private sService: SearchService, private http: HttpClient) {
    // for having the time data
    this.sService.getWeatherData().subscribe(data => {
      var tmptime = data.weatherdata.currently.time;
      this.curtime = JSON.stringify(tmptime);
      var tmplat = data.weatherdata.latitude;
      this.curlat = JSON.stringify(tmplat);
      var tmplon = data.weatherdata.longitude;
      this.curlng = JSON.stringify(tmplon);
      console.log("i am using the detail service to get the time lat and lng");
      console.log(tmptime + "," + tmplat + "," + tmplon);
      this.getDetailWeatherInfo();
    })

  }

  setdetailloc(loc) {
    this.detailLocation.next(loc);
  }

  getdetailloc() {
    return this.detailLocation.asObservable();
  }
  setdetailweather(data){
    console.log("i am trying t set the detailwearher");
    this.detailweatherinfo.next(data);
  }

  getdetailweather(){
    return this.detailweatherinfo.asObservable();
  }

  getDetailWeatherInfo() {
    let tmpapi = this.apicollection.getdetail + "lat=" + this.curlat + "&lng=" + this.curlng + "&time=" + this.curtime;
    let params = new HttpParams()
      .set("lat", this.curlat)
      .set("lng", this.curlng)
      .set("time", this.curtime);

    let response = this.http.get(this.apicollection.getdetail, { params: params });
    response.subscribe(data => {
      this.detailinfoSubject.next(data);
      console.log(data);
      this.setdetailweather(data);
    });
  }

  loadData(){
    console.log("load data");
    console.log(this.detailinfoSubject);
    this.detailweatherinfo.next(this.detailinfoSubject);
  }

  setErrorMsg(data){
    this.errorMSG.next(data);
  }

  getErrorMsg(){
    return this.errorMSG.asObservable();
  }

  setClearData(data){
    this.clear.next(data);
  }

  getClearData(){
    return this.clear.asObservable();
  }
}
