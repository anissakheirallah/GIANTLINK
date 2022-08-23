package com.giantlink.project.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonBackReference
	private User user;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "commercial_id", nullable = false)
	@JsonBackReference
	private Commercial commercial;

	/*
	 * @OneToMany(mappedBy = "lead",fetch=FetchType.EAGER) private Set<Option>
	 * options; //private String options;
	 */

	@OneToMany(mappedBy = "lead", fetch = FetchType.EAGER)
	private Set<Product> products;

	@ManyToMany(mappedBy = "leads", cascade = CascadeType.ALL)
	@JsonBackReference
	private Set<Service> Services;
	// private String Services;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "client_id", nullable = false)
	@JsonBackReference
	private Client client;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date timestamp;

	@PrePersist
	private void onCreate() {
		this.timestamp = new Date();
	}

}
