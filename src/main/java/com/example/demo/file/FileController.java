package com.example.demo.file;

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

import com.example.demo.model.FileData;
import com.example.demo.test.TestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("file")
public class FileController {

	private TestService testService;
	private FileService storageService;

	@Autowired
	public FileController(TestService testService, FileService storageService) {
		this.testService = testService;
		this.storageService = storageService;
	}
	
	@RequestMapping("upload-multi")
	  public ResponseEntity<String> fileupload(List<MultipartFile> file) throws Exception {
		if (file != null) {
	      for (MultipartFile fl : file) {
	        if (fl.getSize() > 0) {
	        	fl.transferTo(new File(fl.getOriginalFilename()));
	        }
	      }
	    } 
	    return new ResponseEntity<>("", HttpStatus.OK);
	  }
	
	@PostMapping(value = "upload")
	public ResponseEntity<String> upload(MultipartFile file) throws IllegalStateException, IOException {
		// FileService.store(file);
		if (!file.isEmpty()) {
			file.transferTo(new File(file.getOriginalFilename()));
		}
		return new ResponseEntity<>("", HttpStatus.OK);
	}

	@GetMapping(value = "download") // localhost:8080/file/download?filename=images.png
	public ResponseEntity<Resource> serveFile(@RequestParam(value = "filename") String filename) {

		Resource file = storageService.loadAsResource(filename);
		System.out.println(file + ": file /////////////");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@PostMapping(value = "delete-all")
	public ResponseEntity<String> deleteAll() {
		storageService.deleteAll();
		;
		return new ResponseEntity<>("", HttpStatus.OK);
	}

	@GetMapping("fileList") // http://localhost:8080/file/fileList
	public ResponseEntity<List<FileData>> getListFiles() {
		List<FileData> fileInfos = storageService.loadAll().map(path -> {
			FileData data = new FileData();
			String filename = path.getFileName().toString();
			data.setFilename(filename);
			data.setUrl(MvcUriComponentsBuilder.fromMethodName(FileController.class, "serveFile", filename).build()
					.toString());
			try {
				data.setSize(Files.size(path));
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			return data;
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
	}
}
