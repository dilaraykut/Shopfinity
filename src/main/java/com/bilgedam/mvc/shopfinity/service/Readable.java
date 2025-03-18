package com.bilgedam.mvc.shopfinity.service;

import java.util.List;

public interface Readable<T, ID> {

	List<T> getList();

	T getById(ID id);

	List<T> getList(String sortBy, String direction);
}
