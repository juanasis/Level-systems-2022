import { Component, OnInit } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import {  UploadFileService } from 'src/app/services/uploadfiles/upload-file.service';
@Component({
    selector: 'upload-files',
    templateUrl: './upload-files.component.html'
  })
export class UploadFilesComponent implements OnInit {

  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';

  fileInfos: Observable<any>;

 constructor(private uploadService: UploadFileService) { }


    // A continuación, definimos el selectFile()método. Nos ayuda a obtener los archivos seleccionados.

 selectFile(event):void {
        this.selectedFiles = event.target.files;
    }

    //A continuación, definimos el upload()método para cargar el archivo:

 upload(): void {
    this.progress = 0;

    this.currentFile = this.selectedFiles.item(0);
    this.uploadService.upload(this.currentFile).subscribe(
        event => {
        if (event.type === HttpEventType.UploadProgress) {
            this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
            this.message = event.body.message;
            this.fileInfos = this.uploadService.getFiles();
        }
        },
        err => {
        this.progress = 0;
        this.message = 'No se pudo cargar el archivo!';
        this.currentFile = undefined;
        });
    this.selectedFiles = undefined;
    }
    /*
    Usamos selectedFiles para acceder al archivo actual como primer elemento. Luego llamamos al 
    uploadService.upload()método en el currentFile.

    El progreso se calculará basándose en event.loadedy event.total.
    Si se realiza la transmisión, el evento será un HttpResponseobjeto. En este momento, 
    llamamos uploadService.getFiles()para obtener la información de los archivos y asignamos
    el resultado a la fileInfosvariable.

    También necesitamos hacer este trabajo en el ngOnInit()método:
    */
 ngOnInit(): void {
    this.fileInfos = this.uploadService.getFiles();
    }
    
 }
