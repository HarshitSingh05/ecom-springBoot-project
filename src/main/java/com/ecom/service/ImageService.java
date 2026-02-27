package com.ecom.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	public String uploadImage(MultipartFile file, String folder);
}
