package com.hsx.oa.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章实体，提供主题实体与回复实体的被继承者
 * 
 * @author hsx
 * 
 */
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title; // 标题
	private String content; // 内容
	private User author; // 作者
	private Date postTime; // 发表时间
	private String ipAddr; // 发表文章时所用的IP地址

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

}
