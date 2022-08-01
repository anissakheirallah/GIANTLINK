package com.giantlink.project.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "services")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String serviceName;
	private Float point;
	private Long idProject;
	private Boolean statut;
	
	

}
