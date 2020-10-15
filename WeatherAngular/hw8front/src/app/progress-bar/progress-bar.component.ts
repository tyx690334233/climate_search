import { Component, OnInit } from '@angular/core';
import { SearchService } from '../services/search.service';

@Component({
  selector: 'app-progress-bar',
  templateUrl: './progress-bar.component.html',
  styleUrls: ['./progress-bar.component.css']
})
export class ProgressBarComponent implements OnInit {

  progress = {
    isshow: false,
    length,
    style: "",
    valuenow: ""
  }
  progressstatue:any;

  constructor(private sService: SearchService) {
    this.sService.getProgress().subscribe(data => {
      console.log(data);
      this.progressstatue = data;
      this.progress.length = this.progressstatue.progress;
      this.progress.isshow = Boolean(this.progressstatue.isshow);
      this.setstyle(this.progress.length);
      this.setValuenow(this.progress.length);
    })
  }

  ngOnInit() {
  }
 
  setstyle(length) {
    // width: 10%
    this.progress.style = "width:" + this.progress.length + "%";
  }
  setValuenow(length) {
    //  10
    this.progress.valuenow = length;
  }
  getstyle(){
    return this.progress.length+"%";
  }
  getValuenow(){
    return this.progress.length;
  }
  // setshow(){
  //   if(this.progress.isshow == false){
  //     return 1;
  //   }
  //   else if(this.progress.isshow == true && this.progress.length< 20){
  //     return 2;
  //   }
  //   else{
  //     return 3;
  //   }
  // }
  setshow(){
    if(this.progress.isshow == false){
      return false;
    }
    else if(this.progress.isshow == true){
      return true;
    }
    else{
      return 3;
    }
  }
}
