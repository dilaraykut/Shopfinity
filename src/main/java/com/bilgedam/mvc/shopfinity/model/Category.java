package com.bilgedam.mvc.shopfinity.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "kategoriler", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "deleted" }))
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Short id;

	@Size(min = 3, max = 30)
	private String name;

	private String enName;
	private boolean deleted;

}
