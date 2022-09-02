package com.giantlink.project.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "packs")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pack {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String packName;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "project_id", nullable = false)
	@JsonBackReference
	private Project project;
	
}