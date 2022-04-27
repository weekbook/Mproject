package kr.icia.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.icia.domain.AttachFileDTO;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UploadController {
	String uploadFolder = "c:\\upload";

	// opg.srpingframework.htpp.MediaType
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody // 응답을 json 형태로 리턴.
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		// rest 방식 으로 ajax 처리
		// 파일을 받고 json 값을 리턴.

		List<AttachFileDTO> list = new ArrayList<>();
		// 여러개 파일 저장을 위한 객체 배열 타입 선언
		String uploadFolder = "c:\\upload";

		String uploadFolderPath = getFolder();
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		// 예) c:\\upload\\2022\05\\27에 파일 저장 예정

		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
			// 경로에 폴더들이 생성되어 있지 않다면, 폴더 생성
		}
		
		// 실제 바이너리 파일의 전송 처리 시작.
		// 파일은 1개 일수도 있고, 여러개 일수도 있음
		for (MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachDTO = new AttachFileDTO();
			String uploadFileName = multipartFile.getOriginalFilename();
			// 파일의 원래 이름 저장.
			// 인터넷 익스플로러 경우, 예외 처리
			uploadFileName = uploadFileName.substring(
					uploadFileName.lastIndexOf("\\")+1);
			// 파일명에 포함된 경로들은 제외하고 실제 파일명만 저장.
			attachDTO.setFileName(uploadFileName); // 파일 이름 저장.
			UUID uuid = UUID.randomUUID();
			// universal unique identifier, 범용 고유 식별자.
			// 파일의 중복을 회피.
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			// 예 ) uuid_일일일.txt
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				// 어느 경로에 어느 파일명으로 저장할지 설정.
				multipartFile.transferTo(saveFile);
				// 서버에 파일 저장.
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				list.add(attachDTO);
				// 업로드된 파일 정보를 객체 배열에 담아서 리턴.
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		// 실제 바이너리 파일의 전송 처리 끝

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
		// 파일 검색 시간을 줄이기 위해서,
		// 폴더 1개에 모두 저장하는 것이 아니라,
		// 년월일로 구분하여 폴더를 생성하고 그곳에 파일을 저장.
		// File.separator : 폴더 구분자르 운영체제에 맞춰서 변경.
		// 2022-04-27
		// 2022/04/27 결과적으로 날짜별로 파일 저장.
	}
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile: " + fileName);
		File file;
		try {
			file=new File("c:\\upload\\" + URLDecoder.decode(fileName,"UTF-8"));
			// 한글의 경우, 페이지 전환시 변경됨.
			// 알맞는 문자 포맷으로 해석해서 읽어 들여야 함.
			file.delete();
			// 파일 삭제.
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
		// return null;
	}
	
	
	
	
	
	
}
