package com.infotree.qliktest.admin.service;

import java.io.Serializable;
import java.util.List;


public interface QTAdminService<T> {

	T getById(Serializable id);
	T save(T t);
	String delete(T t);
	List<T> list();
}

