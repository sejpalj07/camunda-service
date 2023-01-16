package com.incedo.workflow.model;

public class CamundaUser {

private String username;



public CamundaUser(String username) {
	this.username = username;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

@Override
public String toString() {
	return "CamundaUser [username=" + username + "]";
}



}
