package ar.edu.undec.level.service;

import ar.edu.undec.level.storage.entity.Producto;
import ar.edu.undec.level.storage.repository.ProductosRepository;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubirImagenService {

    @Autowired
    private ProductosRepository productosRepository;

    private void uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("uploadimages-76c51.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Resource resource = new ClassPathResource("upload-images-9da08-firebase-adminsdk-jmshj-7e289f0a05.json");
        Credentials credentials = GoogleCredentials.fromStream(Files.newInputStream(Paths.get(resource.getFile().getAbsolutePath())));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Transactional
    public String upload(MultipartFile multipartFile, Integer productoId) {

        Optional<Producto> productoEncontrado = productosRepository.findById(productoId);

        if(!productoEncontrado.isPresent()) throw new RuntimeException("Producto no encontrado");

        try {
            String fileName = multipartFile.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));

            File file = this.convertToFile(multipartFile, fileName);
            this.uploadFile(file, fileName);
            productoEncontrado.get().setImgpath(fileName);
            productosRepository.save(productoEncontrado.get());
            file.delete();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ocurrió un error al subir la imágen.");
        }

    }

    public byte[] download(String fileName) throws IOException {

        Resource resource = new ClassPathResource("upload-images-9da08-firebase-adminsdk-jmshj-7e289f0a05.json");
        Credentials credentials = GoogleCredentials
                .fromStream(Files.newInputStream(Paths.get(resource.getFile().getAbsolutePath())));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(BlobId.of("uploadimages-76c51.appspot.com", fileName));
        return blob.getContent();
    }

    public void eliminarImagen(String archivo) throws IOException {
        Resource resource = new ClassPathResource("upload-images-9da08-firebase-adminsdk-jmshj-7e289f0a05.json");
        Credentials credentials = GoogleCredentials
                .fromStream(Files.newInputStream(Paths.get(resource.getFile().getAbsolutePath())));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        Blob blob = storage.get(BlobId.of("uploadimages-76c51.appspot.com", archivo));
        blob.delete();
    }
}
