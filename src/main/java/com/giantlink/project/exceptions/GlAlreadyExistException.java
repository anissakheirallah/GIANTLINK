package com.giantlink.project.exceptions;

public class GlAlreadyExistException extends Exception {

	private String entityNameAttribut;
	private String entityName;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GlAlreadyExistException(String entityNameAttribut, String entityName) {
		super(entityNameAttribut + " " + entityName + " Already exist !");
		this.entityNameAttribut = entityNameAttribut;
	}

}
