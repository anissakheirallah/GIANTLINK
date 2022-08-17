package com.giantlink.project.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teams")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String teamName;

//	@OneToMany(fetch = FetchType.EAGER, mappedBy = "team")
//	@JsonManagedReference
	@ManyToMany(mappedBy = "teams")
	private Set<User> team_user;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "team")
	@JsonManagedReference
	private Set<Project> projects;
}
