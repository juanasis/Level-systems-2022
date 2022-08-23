import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/models/producto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nuevo',
  templateUrl: './nuevo.component.html',
  styleUrls: ['./nuevo.component.css']
})
export class NuevoComponent implements OnInit {
   selectedProducto: Producto = new Producto();

  
  constructor(private http: HttpClient, private router: Router) { }
  addOnEdit(){    
    console.log(this.selectedProducto.id);
    this.selectedProducto.cantidad = 1;
    this.http.post("http://localhost:8080/productos/agregar", this.selectedProducto).subscribe(
      data  => {
      console.log("POST Request is successful ", data);
      this.router.navigate(['/administrador/productos/page/0'])
      },
      error  => {
    console.log("Error", error);
    }
    )
  }


  ngOnInit(): void {
  }

}
