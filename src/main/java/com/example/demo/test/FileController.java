package com.example.demo.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import com.example.demo.storage.StorageService;
import com.example.demo.model.FileData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("file")
public class FileController {
	
	private TestService testService;
	private StorageService storageService;
	
	@Autowired
	public FileController(TestService testService, StorageService storageService) {
		this.testService = testService;
		this.storageService = storageService;
	}
	
	@PostMapping(value="upload")
	public ResponseEntity<String> upload(MultipartFile file) throws IllegalStateException, IOException{
		//storageService.store(file);
	    if( !file.isEmpty() ) {
	        log.debug("file org name = {}", file.getOriginalFilename());
	        log.debug("file content type = {}", file.getContentType());
	        file.transferTo(new File(file.getOriginalFilename()));
	    }
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@GetMapping(value="download") //localhost:8080/info/download?filename=images.jpg
    public ResponseEntity<Resource> serveFile(@RequestParam(value="filename") String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
	
	@PostMapping(value="deleteAll")
	public ResponseEntity<String> deleteAll(){
	    storageService.deleteAll();;
	    return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@GetMapping("fileList") //http://localhost:8080/info/fileList
    public ResponseEntity<List<FileData>> getListFiles() {
        List<FileData> fileInfos = storageService.loadAll()
          .map(path ->{
              FileData data = new FileData();
              String filename = path.getFileName().toString();
              data.setFilename(filename);
              data.setUrl(MvcUriComponentsBuilder.fromMethodName(FileController.class,
                        "serveFile", filename).build().toString());
              try {
                data.setSize(Files.size(path));
            } catch (IOException e) {
                log.error(e.getMessage());
            }
              return data;
          })
          .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }
}
