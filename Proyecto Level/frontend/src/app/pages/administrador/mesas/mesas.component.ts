import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Mesa } from 'src/app/models/mesa';
import { MesaServiceService } from 'src/app/services/mesa-service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-mesas',
  templateUrl: './mesas.component.html',
  styleUrls: ['./mesas.component.css']
})
export class MesasComponent implements OnInit {

  mesas: Mesa[] = [];

  mesa: Mesa = new Mesa();

  mesaActualizar: Mesa;

  mesaForm = this.fb.group({
    nombre: ['', [Validators.required]],
    activo: [true, [Validators.required]],
  })

  mesaFormActualizar

  constructor(private mesaService: MesaServiceService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.mesaService.listarMesas().subscribe(response => this.mesas = response.data)
  }

  crearMesa(){

    this.mesa.activo = this.activo.value;
    this.mesa.nombre = this.nombre.value;
    console.log(this.mesa)
    this.mesaService.crearMesa(this.mesa)
        .subscribe(response => {
          this.mesas.push(response.data);
          Swal.fire('Mesa creada','La mesa ha sido creada con éxito','success');
        }, error => {
          if(error.status == 400) {
            Swal.fire('Mesa existente', `${error.error.mensaje}`, 'info');
          }
          this.mesaForm.reset();
        });
  }

  actualizarMesa() {
    this.mesaActualizar.activo = this.activoActualizar.value;
    this.mesaActualizar.nombre = this.nombreActualizar.value;
    this.mesaActualizar.pedidos = []; 
    console.log(this.mesaActualizar)
    this.mesaService.actualizarMesa(this.mesaActualizar)
    .subscribe(response => {
      console.log(response)
      Swal.fire('Mesa actualizada','La mesa ha sido actualizada con éxito','success');


      this.mesas.forEach(mesa => {
        if(mesa.id == this.mesaActualizar.id) {
          mesa = this.mesaActualizar
        }
      })

      this.mesaActualizar = undefined;
    }, error => {
      if(error.status == 400) {
        Swal.fire('Mesa existente', `${error.error.mensaje}`, 'info');
      }
      this.mesaActualizar = undefined;
      this.mesaForm.reset();
    });
  }

  seleccionarMesa(mesa: Mesa) {
    this.mesaActualizar = mesa;
    this.mesaFormActualizar = this.fb.group({
      nombre: [mesa.nombre, [Validators.required]],
      activo: [mesa.activo, [Validators.required]],
    })
  }

  cerrarModal() {
    this.mesaActualizar = undefined;
  }

  get nombre() { return this.mesaForm.get('nombre'); }

  get activo() { return this.mesaForm.get('activo'); }

  get nombreActualizar() { return this.mesaFormActualizar.get('nombre'); }

  get activoActualizar() { return this.mesaFormActualizar.get('activo'); }

}
