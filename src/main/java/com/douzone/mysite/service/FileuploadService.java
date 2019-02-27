package com.douzone.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileuploadService 
{
	private static final String SAVE_PATH = "/duzon/uploads";
	private static final String URL = "/uploads/images";
	
	public String restore( MultipartFile multipartFile)
	{
		String url = "";
		
		try
		{
			if( multipartFile.isEmpty() )
			{
				return url;
			}
			
			// 오리지널로 저장하면 덮어버릴수있다 and 한글로 저장될수있음 and 디렉터리가 감당할수있는 파일의수가 정해져있음 그래서 일련번호(자세한 시간으로)로 쫙세우는게 좋음
			// 디렉토리를 여러개만들어둠  시간으로 만든 일련번호의 맨 마지막자리에 맞는 디렉터리에 저장  확장자를 붙여야함 (파일이름이 시간이니까)?
			String originalFileName = multipartFile.getOriginalFilename();
			
			//확장자 분리
			String extName = originalFileName.substring(originalFileName.lastIndexOf('.') + 1);
			
			String saveFileName = generateSaveFileName(extName);
			long filesize = multipartFile.getSize();
			
			System.out.println("###############" + originalFileName);
			System.out.println("###############" + extName);
			System.out.println("###############" + saveFileName);
			System.out.println("###############" + filesize);
	
			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write( fileData );
			os.close();
			
			url = URL + "/" + saveFileName;
		}
		catch (IOException e) 
		{
			new RuntimeException("upload fail");
		}
		return url;
	}
	
	private String generateSaveFileName(String extName)
	{
		String fileName = "";
		Calendar calendar = Calendar.getInstance();
		
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("." + extName);

		return fileName;
	}
}
