package com.gkwang.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gkwang.blog.domain.Catalog;
import com.gkwang.blog.domain.User;
import com.gkwang.blog.repository.CatalogRepository;

@Service
public class CatalogServiceImpl  implements CatalogService{
	
	@Autowired
	private CatalogRepository catalogRepository;

	@Override
	public Catalog saveCatalog(Catalog catalog) {
		// TODO Auto-generated method stub
		List<Catalog> list = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());
		if (list!=null&&list.size()>0) {
			throw new IllegalArgumentException("该分类已经存在");
		}
		Catalog save = catalogRepository.save(catalog);
		return save;
	}

	@Override
	public void removeCatalog(Integer id) {
		// TODO Auto-generated method stub
		catalogRepository.delete(id);
	}

	@Override
	public Catalog getById(Integer id) {
		// TODO Auto-generated method stub
		Catalog catalog  = catalogRepository.findOne(id);
		return catalog;
	}

	@Override
	public List<Catalog> listCatalogs(User user) {
		// TODO Auto-generated method stub
		return catalogRepository.findByUser(user);
	}

}
