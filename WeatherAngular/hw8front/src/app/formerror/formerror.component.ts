import { Component, OnInit, Input } from '@angular/core';
import { DetailService } from '../services/detail.service';

@Component({
  selector: 'app-formerror',
  templateUrl: './formerror.component.html',
  styleUrls: ['./formerror.component.css']
})
export class FormerrorComponent implements OnInit {

  @Input() notFullfill: any;
  errorinfo;
  errorString;
  constructor(private dService: DetailService) {
    dService.getErrorMsg().subscribe(data => {
      this.errorString = data;
      if (this.errorString != null && this.errorString.length != 0) {
        this.errorinfo = data;
      }
      else{
        this.errorinfo = null;
      }
    })
  }

  ngOnInit() {
    this.geterroeinfo();
  }

  // todo : using local storage to show the error msg
  geterroeinfo() {
    this.errorinfo = localStorage.getItem("errorMsg");
  }
}
