package com.example.demo.file;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    
    void init();

    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

	Resource loadAsResource(String filename);

    void deleteAll();
    
}
