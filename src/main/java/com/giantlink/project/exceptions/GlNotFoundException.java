package com.giantlink.project.exceptions;

public class GlNotFoundException extends Exception {

	private String entityNameAttribut;
	private String entityName;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GlNotFoundException(String entityNameAttribut, String entityName) {
		super(entityNameAttribut + " " + entityName + " Not Found !");
		this.entityNameAttribut = entityNameAttribut;
	}

}