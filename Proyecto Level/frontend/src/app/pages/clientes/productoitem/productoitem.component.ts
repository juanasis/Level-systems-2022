import { Component, Input, OnInit } from '@angular/core';
import { Producto } from 'src/app/models/producto';


@Component({
  selector: 'app-productoitem',
  templateUrl: './productoitem.component.html',
  styleUrls: ['./productoitem.component.css']
})
export class ProductoitemComponent implements OnInit {
  @Input() producto: Producto; 
  URL_BACKEND: string = "http://localhost:8080/api/imagenes/";
  constructor( ) { }
  
  ngOnInit(): void {
     

  }
}
