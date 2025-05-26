package com.board.board.service.implement;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.board.board.repository.UserRepository;
import com.board.board.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImplement implements FileService{
	
	@Value("${file.path}")
	private String filePath;
	
	@Value("${file.url}")
	private String fileUrl;
	
	@Override
	public String upload(MultipartFile file) {
		if(file.isEmpty()) return null;
		
		String originlFileName = file.getOriginalFilename();
		String extension = originlFileName.substring(originlFileName.lastIndexOf("."));
		String uuid = UUID.randomUUID().toString();
		String saveFileName = uuid + extension;
		String savePath = filePath + saveFileName;
		
		try {
			file.transferTo(new File(savePath));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		String url = fileUrl + saveFileName;
		return url;
	}

	@Override
	public Resource getImage(String fileName) {
		
		Resource resource = null;
		
		try {
			resource = new UrlResource("file:///" + filePath + fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return resource;
	}

}
