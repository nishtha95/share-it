package com.shareit.controllers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shareit.dtos.FileDTO;
import com.shareit.exception.ShareItException;
import com.shareit.models.FileDAO;
import com.shareit.services.FileService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@Slf4j
public class FileController {

	@Autowired
	FileService fileService;
	
	@RequestMapping(value = "/uploadFile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, method = RequestMethod.POST)
	public ResponseEntity<?> uploadToDB(@RequestPart("file") MultipartFile file,@RequestPart FileDTO fileDTO) {
		String fileDownloadUri=null;
		try {
			fileDownloadUri = fileService.uploadFile(fileDTO, file);
		} catch (ShareItException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(fileDownloadUri);
	}
	
	@RequestMapping("/files/download/{id}/{title}")
	public ResponseEntity<?> downloadFromDB(@PathVariable String id,@PathVariable String title) {
		FileDAO file=null;
		try {
			file = fileService.downloadFile(id,title);
		} catch (ShareItException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getTitle() + "\"")
				.body(file.getFile());
	}
	
	@RequestMapping("/files")
	public ResponseEntity<?> fetchAllFiles() {
		List<FileDTO> files=fileService.fetchAllFiles();
		return ResponseEntity.ok(files);
	}
	
	@RequestMapping(value = "/file/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFile(@PathVariable String id) {
		String response=null;
		try {
			response = fileService.deleteFile(Long.parseLong(id));
		} catch (ShareItException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(response);
	}
}
