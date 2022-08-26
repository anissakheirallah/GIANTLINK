package com.giantlink.project.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "projects")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String projectName;
	private String projectType;
	private Date startDate;
	private Date finishDate;

	@OneToMany(orphanRemoval = false, mappedBy = "project")
	@JsonManagedReference
	private Set<Team> teams;

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	private Set<Option> options;
}
