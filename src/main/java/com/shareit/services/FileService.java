package com.shareit.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.shareit.dtos.FileDTO;
import com.shareit.exception.ShareItException;
import com.shareit.models.FileDAO;
import com.shareit.models.UserDAO;
import com.shareit.repositories.FileRepository;
import com.shareit.repositories.UserRepository;

@Service
public class FileService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FileRepository fileRepository;
	
	@Transactional
	public String uploadFile(FileDTO fileDTO,MultipartFile file) throws ShareItException {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails=(UserDetails)auth.getPrincipal();
		UserDAO user=userRepository.findByUsername(userDetails.getUsername());
		FileDAO fileDAO=new FileDAO();
		fileDAO.setTitle(fileDTO.getTitle());
		fileDAO.setDescription(fileDTO.getDescription());
		try {
			fileDAO.setFile(file.getBytes());
		} catch (IOException e) {
			throw new ShareItException(e.getMessage(),e.getCause());
		}
		
		fileDAO.setUser(user);
		fileDAO= fileRepository.save(fileDAO);
		System.out.println(fileDAO.getId());
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/files/download/")
				.path(fileDAO.getId().toString())
				.path("/")
				.path(fileDAO.getTitle()).path("/db")
				.toUriString();
		return fileDownloadUri;
	}
	
	public FileDAO downloadFile(String id,String title) throws ShareItException{
		FileDAO fileDAO=fileRepository.findById(Long.parseLong(id)).orElse(null);
		if(fileDAO==null){
			throw new ShareItException("Invalid File URL");
		}
		if(!fileDAO.getTitle().equals(title)){
			throw new ShareItException("Invalid File URL");
		}
		return fileDAO;
	}

	public List<FileDTO> fetchAllFiles() {
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails=(UserDetails)auth.getPrincipal();
		UserDAO user=userRepository.findByUsername(userDetails.getUsername());
		List<FileDAO> fileDAOs= user.getFiles();
		List<FileDTO> fileDTOs=new ArrayList<>();
		for(FileDAO file: fileDAOs){
			FileDTO fileDTO=new FileDTO();
			fileDTO.setId(file.getId());
			fileDTO.setTitle(file.getTitle());
			fileDTO.setDescription(file.getDescription());
			fileDTO.setCreatedAt(file.getCreatedAt());
			fileDTOs.add(fileDTO);
		}
		return fileDTOs;
	}

	@Transactional
	public String deleteFile(FileDTO fileDTO) throws ShareItException{
		FileDAO fileDAO=fileRepository.findById(fileDTO.getId()).orElse(null);
		if(fileDAO!=null){
			fileRepository.delete(fileDAO);
		}
		else {
			throw new ShareItException("No such file exists");
		}
		
		return "File Deleted Successfully";
	}
}
