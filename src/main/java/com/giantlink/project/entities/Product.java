package com.giantlink.project.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String productName;
	private Long idProject;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "lead_id", nullable = false)
	@JsonBackReference
	private Lead lead;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date timestamp;
	
	@PrePersist
	private void onCreate() {
		this.timestamp = new Date();
	}
	
	
	
}
