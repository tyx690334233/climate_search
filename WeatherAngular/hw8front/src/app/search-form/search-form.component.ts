import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormControl } from '@angular/forms';
import { HttpClient, HttpParams } from '@angular/common/http';
import { SearchService } from '../services/search.service';
import { DetailService } from '../services/detail.service';
import { API } from '../apilink';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { ValidationDirective } from './validation.directive';


@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {

  search = {
    streetname: '',
    cityname: '',
    statename: 'Select State',
    isCurLocChoose: {
      checked: false
    },
    isEditable: true
  };
  apicollection = API;
  stateList = [
    { abbreviation: "AL", name: "Alabama" },
    { abbreviation: "AK", name: "Alaska" },
    { abbreviation: "AZ", name: "Arizona" },
    { abbreviation: "AR", name: "Arkansas" },
    { abbreviation: "CA", name: "California" },
    { abbreviation: "CO", name: "Colorado" },
    { abbreviation: "CT", name: "Connecticut" },
    { abbreviation: "DE", name: "Delaware" },
    { abbreviation: "DC", name: "District Of Columbia" },
    { abbreviation: "FL", name: "Florida" },
    { abbreviation: "GA", name: "Georgia" },
    { abbreviation: "HI", name: "Hawaii" },
    { abbreviation: "ID", name: "Idaho" },
    { abbreviation: "IL", name: "Illinois" },
    { abbreviation: "IN", name: "Indiana" },
    { abbreviation: "IA", name: "Iowa" },
    { abbreviation: "KS", name: "Kansas" },
    { abbreviation: "KY", name: "Kentucky" },
    { abbreviation: "LA", name: "Louisiana" },
    { abbreviation: "ME", name: "Maine" },
    { abbreviation: "MD", name: "Maryland" },
    { abbreviation: "MA", name: "Massachusetts" },
    { abbreviation: "MI", name: "Michigan" },
    { abbreviation: "MN", name: "Minnesota" },
    { abbreviation: "MS", name: "Mississippi" },
    { abbreviation: "MO", name: "Missouri" },
    { abbreviation: "MT", name: "Montana" },
    { abbreviation: "NE", name: "Nebraska" },
    { abbreviation: "NV", name: "Nevada" },
    { abbreviation: "NH", name: "New Hampshire" },
    { abbreviation: "NJ", name: "New Jersey" },
    { abbreviation: "NM", name: "New Mexico" },
    { abbreviation: "NY", name: "New York" },
    { abbreviation: "NC", name: "North Carolina" },
    { abbreviation: "ND", name: "North Dakota" },
    { abbreviation: "OH", name: "Ohio" },
    { abbreviation: "OK", name: "Oklahoma" },
    { abbreviation: "OR", name: "Oregon" },
    { abbreviation: "PA", name: "Pennsylvania" },
    { abbreviation: "RI", name: "Rhode Island" },
    { abbreviation: "SC", name: "South Carolina" },
    { abbreviation: "SD", name: "South Dakota" },
    { abbreviation: "TN", name: "Tennessee" },
    { abbreviation: "TX", name: "Texas" },
    { abbreviation: "UT", name: "Utah" },
    { abbreviation: "VT", name: "Vermont" },
    { abbreviation: "VA", name: "Virginia" },
    { abbreviation: "WA", name: "Washington" },
    { abbreviation: "WV", name: "West Virginia" },
    { abbreviation: "WI", name: "Wisconsin" },
    { abbreviation: "WY", name: "Wyoming" }
  ];
  options: string[] = ['One', 'Two', 'Three'];
  filteredOptions: Observable<string[]>;
  adviseOption: Observable<string[]>;
  weatherform: FormGroup;

  curLat;
  curLon;
  curCity;
  curState;
  // this is the weather data info
  weatherdata;
  tmplocdata;
  progressStatus = {
    progress: 0,
    isshow: false
  }
  curdata;
  constructor(public http: HttpClient, private sService: SearchService, private dService: DetailService) {
    this.sService.getResubmitForm().subscribe(data => {
      this.curdata = data;
      let cur = JSON.parse(this.curdata);
      console.log(cur);
      let curCity = cur.cityname;
      let curState = cur.statename;
      this.ResubmitSearchForm(curCity, curState);

    })
  }

  ngOnInit(): void {
    this.weatherform = new FormGroup({
      'street': new FormControl({ value: this.search.streetname, disabled: !this.search.isEditable },
        [Validators.required, ValidationDirective]
      ),
      'city': new FormControl({ value: this.search.cityname, disabled: !this.search.isEditable },
        [Validators.required, ValidationDirective]
      ),
      'state': new FormControl({ value: this.search.statename, disabled: !this.search.isEditable },
        Validators.required
      ),
      'curloc': new FormControl(this.search.isCurLocChoose)
    })
    this.setProgress(0, false);
    this.adviseOption = this.weatherform.get("city").valueChanges
      .pipe(
        startWith(''),
        map(value => this._filter(value))
      );
  }
  tmpJSON: any;
  private _filter(value: string) {
    if (value == null) {
      return;
    }
    const filterValue = value.toLowerCase();
    // console.log("your input is:" + filterValue);
    let api = this.apicollection.getautocomplete;
    let param = new HttpParams()
      .set("input", filterValue);
    // call the api to get autocomplete context
    let promise = this.http.get(api, { params: param });
    let tmparray = [];
    promise.subscribe(data => {
      console.log(data);
      this.tmpJSON = data;

      for (let item in this.tmpJSON.predictions) {
        let tmpitem = this.tmpJSON.predictions[item].terms[0].value;
        console.log(tmpitem);
        tmparray.push(tmpitem);
      }
    })
    // this.
    // return this.options.filter(option => option.toLowerCase().includes(filterValue));
    return tmparray;
  }

  get streetname() {
    return this.weatherform.get('street');
  }

  get cityname() {
    return this.weatherform.get('city');
  }

  clearAll(): void {
    this.search.cityname = "";
    this.search.streetname = "";
    this.search.statename = "Select State";
    this.search.isEditable = true;
    this.search.isCurLocChoose.checked = false;
    this.sService.setResultShow(false);
    sessionStorage.removeItem("city");
    sessionStorage.removeItem("customUrl");
    this.dService.setErrorMsg("");
    this.dService.setClearData(true);
    this.setProgress(0, false);
  }

  useCurLoc(): void {
    this.search.isCurLocChoose.checked = !this.search.isCurLocChoose.checked;
    this.search.isEditable = !this.search.isEditable;
    console.log("checkstate:" + this.search.isCurLocChoose.checked);
    console.log("isEditablestate:" + this.search.isEditable);
    const iscurState = this.search.isEditable ? 'enable' : 'disable';
    Object.keys(this.weatherform.controls).forEach((controlName) => {
      this.weatherform.controls[controlName][iscurState](); // disables/enables each form control based on 'this.formDisabled'
    });
    // this means the using current location
    // if (iscurState == "disable") {
    //   // empty other element, 
    //   // this.search.cityname ="";
    //   // this.search.streetname ="";
    //   // this.search.statename="Select State";
    //   // and get the curr lat,lon
    //   this.getLocData();
    // }
  }

  getLocData(): void {
    let api = "http://ip-api.com/json";
    this.http.get(api).subscribe((response: any) => {
      console.log(response);
      this.curLat = response.lat;
      this.curLon = response.lon;
      this.curCity = response.city;
      this.curState = response.region;
      // this.sService.setCity(response.city);
      // this.sService.setState(response.region);
    })
  }

  isFillNeededInfo(): boolean {
    var setLoc = false;
    if ((this.search.streetname != null && this.search.streetname != "") && (this.search.cityname != null && this.search.cityname != "")) {
      setLoc = true;
    }
    return setLoc;
  }

  submitSearchForm(city = this.search.cityname, state = this.search.statename) {
    sessionStorage.removeItem("weather");
    let fullFilled = this.isFillNeededInfo();
    this.dService.setClearData(false);
    this.sService.setResultShow(false);
    this.sService.setFavShow(false);
    // set progressbar
    this.setProgress(0, true);
    // set to show the erroer msg
    if (!fullFilled) {

    }

    // if this is a valid form , send the data to backend
    if (fullFilled && !this.search.isCurLocChoose.checked) {
      console.log("here");
      // this.sService.setState(state);
      this.fetchWeatherData(city, state);
    } else if (this.search.isCurLocChoose.checked) {
      console.log("there");
      // this.sService.setState(this.curState);
      let api = "http://ip-api.com/json";
      this.http.get(api).subscribe((response: any) => {
        console.log(response);
        this.curLat = response.lat;
        this.curLon = response.lon;
        this.curCity = response.city;
        this.curState = response.region;
        // this.sService.setCity(response.city);
        // this.sService.setState(response.region);
        this.fetchCurLocWeatherData();
      })
      
    }
    this.setProgress(50, true);
    // console.log(this.search);
    // this.sService.setResultShow(true);
    // this.sService.setWeatherData(this.weatherdata);
    this.setProgress(50, true);
  }

  private fetchWeatherData(city = this.search.cityname, state = this.search.statename) {

    // http://localhost:3000/server/getbylocation?street=1246 W 30th St&city=Los Angeles&state=CA
    // var api = this.apicollection.getbyLoca + "street=" + this.search.streetname + "&city=" + this.search.cityname + "&state=" + this.search.statename;
    console.log("fetch weather data");
    var api = this.apicollection.getlocation + "street=" + encodeURIComponent(this.search.streetname) + "&city=" + encodeURIComponent(city) + "&state=" + encodeURIComponent(state);
    const promise = this.http.get(api).toPromise();
    // console.log(promise);
    promise.then((data) => {
      // this is to get the lat and lng
      console.log("i am using the getlocation api");

      this.tmplocdata = data;
      var tmpres = this.tmplocdata.results[0].geometry.location;
      // console.log(tmpres);
      this.curLat = tmpres.lat;
      this.curLon = tmpres.lng;
      // if(this.tmplocdata.results == 0){
      //   console.log("shit");
      // }
      // return tmpres;
    }).then(data => {
      var getweatherapi = this.apicollection.getweather + "lat=" + this.curLat + "&lng=" + this.curLon;
      var promise = this.http.get(getweatherapi).toPromise();
      // this is for the weather info
      promise.then((data) => {
        // console.log("using the street name to fetch" + data);
        // this.sService.setWeatherData(data);
        let tmp = {
          weatherdata: data,
          state: state,
          city: city
        }
        console.log("i am sending new format weather info");
        this.sService.setWeatherData(tmp);
      })
    }).catch((error) => {
      console.log("Promise rejected with " + JSON.stringify(error));
      console.log("invalid address");
      this.sService.setFavShow(false);
      this.sService.setResultShow(false);
      this.dService.setErrorMsg("invalid address");
      this.setProgress(50, false);
    });
    // set progressbar
    this.setProgress(20, true);
  }

  // using current lovation to fetch data
  private fetchCurLocWeatherData() {
    // http://localhost:3000/server/get_weather?lat=37.8267&lng=-122.4233
    var api = this.apicollection.getweather + "lat=" + this.curLat + "&lng=" + this.curLon;
    // console.log(api);
    const promise = this.http.get(api).toPromise();
    // console.log(promise);
    promise.then((data) => {
      // console.log("Promise resolved with: " + JSON.stringify(data));
      console.log("using the loc and lng to fetch" + data);
      // this.sService.setWeatherData(data);
      let tmp = {
        weatherdata: data,
        state: this.curState,
        city: this.curCity
      }
      console.log("i am sending new format weather info");
      this.sService.setWeatherData(tmp);
    }).catch((error) => {
      console.log("Promise rejected with " + JSON.stringify(error));
    });
    // set progressbar
    this.setProgress(50, true);
  }

  showErrorMsg(): void {
    let isfullfilled = false;
    // when cur loc is not checked and not fill all the needed info
    if (!this.isFillNeededInfo() && this.search.isCurLocChoose.checked == false) {
      isfullfilled = true;
    }

    isfullfilled = false;
    localStorage.setItem("errorMsg", String(isfullfilled));
  }

  // set the status of progress bar
  setProgress(length: number, status: boolean) {
    this.progressStatus.progress = length;
    this.progressStatus.isshow = status;
    this.sService.setProgress(this.progressStatus);
  }
  ResubmitSearchForm(city = this.search.cityname, state = this.search.statename) {

    // set progressbar
    this.setProgress(0, true);
    this.dService.setClearData(false);
    console.log("here");
    this.fetchWeatherData(city, state);
  }


}
