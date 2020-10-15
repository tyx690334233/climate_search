import { Injectable } from '@angular/core';
import { of, Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class SearchService {
  weatherInfo = new Subject<any>();
  city = new Subject();
  state = new Subject();
  customSearch = new Subject();

  showResult = new Subject();
  showFavourite = new Subject();

  progress = new Subject();

  stateSeal = new Subject();

  resubmitForm = new Subject();

  constructor() { }

  get() {
    return "this is a service";
  }

  setWeatherData(weather) {
    // console.log(weather);
    console.log("i am setting weather data through search service");
    this.weatherInfo.next(weather);
    // console.log("i am saveing the wwather data");
    sessionStorage.setItem("weather", JSON.stringify(weather.weatherdata));
  }

  getWeatherData() {
    console.log("iam using get weather data");
    return this.weatherInfo.asObservable();
  }
  // setter and getter for city name
  // setCity(city) {
  //   console.log(city);
  //   this.city.next(city);
  // }

  // getCity() {
  //   return this.city.asObservable();
  // }
  // setter and getter for the state name
  // setState(state: String) {
  //   console.log(state);
  //   this.state.next(state);
  //   // meanwhile , get the pic of the state

  // }

  getState() {
    return this.state.asObservable();
  }

  setCustomSearch(url) {
    this.customSearch.next(url);
  }

  getCustomSearch() {
    return this.customSearch.asObservable();
  }

  setResultShow(isShow) {
    console.log("i change the result show state"+isShow);
    this.showResult.next(isShow);
  }

  getResultShow() {
    return this.showResult.asObservable();
  }

  setProgress(pro) {
    this.progress.next(pro);
  }

  getProgress() {
    return this.progress.asObservable();
  }

  setStateSeal(url){
    this.stateSeal.next(url);
  }

  getStateSeal(){
    return this.stateSeal.asObservable();
  }

  setFavShow(isShow) {
    this.showFavourite.next(isShow);
  }

  getFavShow() {
    return this.showFavourite.asObservable();
  }

  setResubmit(data){
    this.resubmitForm.next(data);
  }

  getResubmitForm(){
    return this.resubmitForm.asObservable();
  }

}
