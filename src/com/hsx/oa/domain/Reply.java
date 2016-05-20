package com.hsx.oa.domain;

import java.io.Serializable;

public class Reply extends Article implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Topic topic; // 所属的主题
	
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	public Topic getTopic() {
		return topic;
	}
	
}
