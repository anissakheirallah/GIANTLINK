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
@Table(name = "commercials")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commercial {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String commercialName;
	private Boolean statut;
	private Long idCalendar;


}
