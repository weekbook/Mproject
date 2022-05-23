package com.review.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.review.domain.AttachFileDto;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UploadController {
	String uploadFolder = "c:\\upload";
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDto>> uploadAjaxPost(MultipartFile[] uploadFile){
		List<AttachFileDto> list = new ArrayList<>();
		
		String uploadFolderPath = getFolder();
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			AttachFileDto attachFileDto = new AttachFileDto();
			String uploadFileName = multipartFile.getOriginalFilename();
			uploadFileName = uploadFileName.substring(
					uploadFileName.lastIndexOf("\\") + 1);
			attachFileDto.setFileName(uploadFileName);
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				attachFileDto.setUuid(uuid.toString());
				attachFileDto.setUploadPath(uploadFolderPath);
				list.add(attachFileDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private String getFolder() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = simpleDateFormat.format(date);
		return str.replace("-", File.separator);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("파일삭제" + fileName);
		File file;
		try {
			file = new File("c:\\upload\\" + URLDecoder.decode(fileName,"UTF-8"));
			file.delete();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("delete",HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/download",
			produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(
			@RequestHeader("User-Agent") String userAgent, String fileName){
		// 서버에 접속한 브라우저의 정보는
		// 헤더의 User-Agent를 보면 알 수 있음.
		Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
		// 파일을 리소스(자원: 가공 처리를 위한 중간 단계]로 변경.
		log.info("resource: " + resource);
		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		String resourceName = resource.getFilename();
		// 리소스에서 파일명을 찾아서 할당.
		String resourceOriginalName
		= resourceName.substring(resourceName.indexOf("_") + 1);
		// uuid를 제외한 파일명 만들기.
		HttpHeaders headers = new HttpHeaders();
		// 웹 브라우저별 특성 처리
		try {
			String downloadName = null;
			if (userAgent.contains("Trident")) {
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginalName
						, "UTF-8").replaceAll("\\", " ");
			} else if (userAgent.contains("Edge")) {
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
			} else {
				log.info("chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8")
						, "ISO-8859-1");
			}
			log.info("downloadName: " + downloadName);
			headers.add("Content-disposition", "attachment; filename=" + downloadName);
			// 헤더에 파일 다운로드 정보 추가.
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	
	
	
}
