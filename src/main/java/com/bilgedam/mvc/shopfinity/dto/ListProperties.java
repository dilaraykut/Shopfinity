package com.bilgedam.mvc.shopfinity.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListProperties implements Serializable{

	private String sortBy;
	private String direction;
	private int page;
	private int size;
	private String keyword;
}
