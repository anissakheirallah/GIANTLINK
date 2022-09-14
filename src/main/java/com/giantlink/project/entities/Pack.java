package com.giantlink.project.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Table(indexes = @Index(columnList = "projectId"), name="packs")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pack implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String packName;
	
//	@ManyToOne(cascade = CascadeType.PERSIST)
//	@JoinColumn(name = "project_id", nullable = false)
//	@JsonBackReference
//	private Project project;

	private Long projectId;
	
}
