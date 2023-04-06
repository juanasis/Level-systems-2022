package ar.edu.undec.level.controller;

import ar.edu.undec.level.service.SubirImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api/imagenes")
@CrossOrigin(origins = {"*"})
public class UploadImageController {

    @Autowired
    private SubirImagenService subirImagenService;

    @PostMapping("/subir")
    public void uploadPicture(@RequestParam("file") MultipartFile multipartFile,
                                @RequestParam("productoId") String productoId) {
        subirImagenService.upload(multipartFile, Integer.parseInt(productoId));
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) throws IOException {
        byte[] data = subirImagenService.download(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }


}
