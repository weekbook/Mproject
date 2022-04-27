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
	@ResponseBody // ������ json ���·� ����.
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		// rest ��� ���� ajax ó��
		// ������ �ް� json ���� ����.

		List<AttachFileDTO> list = new ArrayList<>();
		// ������ ���� ������ ���� ��ü �迭 Ÿ�� ����
		String uploadFolder = "c:\\upload";

		String uploadFolderPath = getFolder();
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		// ��) c:\\upload\\2022\05\\27�� ���� ���� ����

		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
			// ��ο� �������� �����Ǿ� ���� �ʴٸ�, ���� ����
		}
		
		// ���� ���̳ʸ� ������ ���� ó�� ����.
		// ������ 1�� �ϼ��� �ְ�, ������ �ϼ��� ����
		for (MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachDTO = new AttachFileDTO();
			String uploadFileName = multipartFile.getOriginalFilename();
			// ������ ���� �̸� ����.
			// ���ͳ� �ͽ��÷η� ���, ���� ó��
			uploadFileName = uploadFileName.substring(
					uploadFileName.lastIndexOf("\\")+1);
			// ���ϸ� ���Ե� ��ε��� �����ϰ� ���� ���ϸ� ����.
			attachDTO.setFileName(uploadFileName); // ���� �̸� ����.
			UUID uuid = UUID.randomUUID();
			// universal unique identifier, ���� ���� �ĺ���.
			// ������ �ߺ��� ȸ��.
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			// �� ) uuid_������.txt
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				// ��� ��ο� ��� ���ϸ����� �������� ����.
				multipartFile.transferTo(saveFile);
				// ������ ���� ����.
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				list.add(attachDTO);
				// ���ε�� ���� ������ ��ü �迭�� ��Ƽ� ����.
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		// ���� ���̳ʸ� ������ ���� ó�� ��

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
		// ���� �˻� �ð��� ���̱� ���ؼ�,
		// ���� 1���� ��� �����ϴ� ���� �ƴ϶�,
		// ����Ϸ� �����Ͽ� ������ �����ϰ� �װ��� ������ ����.
		// File.separator : ���� �����ڸ� �ü���� ���缭 ����.
		// 2022-04-27
		// 2022/04/27 ��������� ��¥���� ���� ����.
	}
	
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile: " + fileName);
		File file;
		try {
			file=new File("c:\\upload\\" + URLDecoder.decode(fileName,"UTF-8"));
			// �ѱ��� ���, ������ ��ȯ�� �����.
			// �˸´� ���� �������� �ؼ��ؼ� �о� �鿩�� ��.
			file.delete();
			// ���� ����.
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<> (HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
		// return null;
	}
	
	
	
	
	
	
}
