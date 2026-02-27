package com.ecom.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ecom.service.ImageService;

@Service
public class ImageServiceImple implements ImageService {
	
	@Autowired
	private Cloudinary cloudinary;

	@Override
	public String uploadImage(MultipartFile file, String folder) {
		
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", folder)
            );

            // This is FULL URL -> store directly in DB
            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}

}
