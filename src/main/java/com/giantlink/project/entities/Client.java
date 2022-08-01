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
@Table(name = "clients")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private String adress;
	private String tele;
	private String email;
	private String GSM;
	private String CP;
	private String city;
	
	
	

}
