package network.sercuritySocket.com.lwx.bean;

import java.io.Serializable;

public class User implements Serializable{
	
	
	/**
	 * java.io.InvalidClassException: com.lwx.bean.User; local class incompatible: stream classdesc serialVersionUID = 1, local class serialVersionUID = 8089718401965834129

	 */
	private static final long serialVersionUID = 8089718401965834129L;
	public User() {

	}

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	private int id;
	private String name;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
