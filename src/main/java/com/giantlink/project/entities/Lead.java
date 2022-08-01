package com.giantlink.project.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "leads")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lead {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@ManyToOne()
	private Client client;
	
	@ManyToOne()
	private User user;
	
	@ManyToOne()
	private Commercial commercial;
	
	@OneToMany
	private String options; 
	
	private String Services;

	

}
