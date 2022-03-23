package ar.edu.undec.level;

import ar.edu.undec.level.uploadingfiles.storage.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class LevelAplication implements CommandLineRunner {
    @Resource
    FilesStorageService storageService;
    public static void main(String[] args) { SpringApplication.run(LevelAplication.class, args); }


    @Override
    public void run(String... arg) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }

}
