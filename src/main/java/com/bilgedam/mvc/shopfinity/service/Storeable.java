package com.bilgedam.mvc.shopfinity.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface Storeable {
	
	void save(MultipartFile file) throws IOException;

	
	Resource load(String fileName);

}
