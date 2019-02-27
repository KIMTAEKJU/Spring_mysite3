package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryDao 
{
	@Autowired
	private SqlSession sqlsession;
	
	public int insert(GalleryVo galleryVo)
	{
		System.out.println("galleryVo comment : " + galleryVo.getComment());
		System.out.println("galleryVo url : " + galleryVo.getImageUrl());

		return sqlsession.insert("gallery.insert", galleryVo);
	}
	
	public List<GalleryVo> select()
	{
		return sqlsession.selectList("gallery.getGalleryList");
	}
	
	public int delete(long no)
	{
		return sqlsession.delete("gallery.delete", no);
	}
}
