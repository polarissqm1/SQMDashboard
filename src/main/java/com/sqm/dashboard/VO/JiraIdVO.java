package com.sqm.dashboard.VO;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;

@Component
public class JiraIdVO extends BasicDBObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String jiraid;

	public String getJiraid() {
		return jiraid;
	}
	public void setJiraid(String jiraid) {
		this.jiraid = jiraid;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	private String env;
}
