package com.douzone.mysite.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.repository.GalleryDao;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService 
{
	@Autowired
	private GalleryDao galleryDao;

	public int insert(GalleryVo galleryVo, String imageUrl)
	{
		galleryVo.setImageUrl(imageUrl);
		return galleryDao.insert(galleryVo);
	}
	
	public int delete(long no)
	{
		return galleryDao.delete(no);
	}

	public List<GalleryVo> getGalleryList()
	{
		return galleryDao.select();
	}
}
