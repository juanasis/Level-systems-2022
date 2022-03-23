import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Mesa } from 'src/app/models/mesa';

@Component({
  selector: 'app-mozos',
  templateUrl: './mozos.component.html',
  styleUrls: ['./mozos.component.css']
})
export class MozosComponent implements OnInit {
  mesas: Mesa[] = [];
  
  
  constructor(private http: HttpClient) { }
  ngOnInit(): void {
    this.http.get("http://localhost:8080/mesas",{responseType: 'json'}).subscribe(
      (resp:any) =>{
     
      this.mesas = resp.data;
      console.log( resp.data);
      })
  }
  

}
