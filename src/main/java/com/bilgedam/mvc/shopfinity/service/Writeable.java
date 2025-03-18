package com.bilgedam.mvc.shopfinity.service;

public interface Writeable<T, ID> {

	void add(T entity);

	void change(ID id, T entity);

	void remove(ID id);
}
