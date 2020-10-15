import { Component, OnInit } from '@angular/core';
import {SearchService} from '../services/search.service';
import {DetailService} from '../services/detail.service';

@Component({
  selector: 'app-result-container',
  templateUrl: './result-container.component.html',
  styleUrls: ['./result-container.component.css']
})
export class ResultContainerComponent implements OnInit {

  resShowClass: string = "btn resActive";
  favShowClass: string = "btn btn-light";
  // isShowRes:boolean = false;
  isShowRes:boolean = false;
  isShowFav:boolean = false;
  clearALL=false;
  constructor(private sService:SearchService,private dService:DetailService) { 
    this.sService.getResultShow().subscribe(data=>{
      let tmp = data;
      // console.log("show state changed:"+ tmp);
      this.isShowRes = Boolean(tmp);
    })
    this.sService.getFavShow().subscribe(data=>{
      this.isShowFav = Boolean(data);
    });
    this.dService.getClearData().subscribe(data=>{
      this.clearALL = Boolean(data);
    })
  }

  ngOnInit() {
  }
  setActive($event): void {
    console.log($event);
  }

  showRes(): void {
    this.isShowRes = true;
    this.isShowFav = false;
    this.resShowClass = "btn resActive";
    this.favShowClass = "btn btn-light"
  }

  showFav(): void {
    this.isShowFav = true;
    this.isShowRes = false;
    this.resShowClass = "btn btn-light";
    this.favShowClass = "btn resActive"
  }

  getShow(){
    // !isShowData
    // this.isShowRes means whether show the res
    // this.clearALL means whether have bean cleared
    if(this.isShowRes == true && this.clearALL == true){
      return false;
    }
    if(this.isShowRes == true && this.clearALL == false){
      return true;
    }
    if(this.isShowRes == false && this.clearALL == true){
      return false;
    }
    if(this.isShowRes == false && this.clearALL == false){
      return false;
    }
  }
}
