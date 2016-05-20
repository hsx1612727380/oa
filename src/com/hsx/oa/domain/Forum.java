package com.hsx.oa.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 版块管理
 * 
 * @author hsx
 * 
 */
public class Forum implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String description;
	private int position; // 排序使用 : 上移、下移
	private Set<Topic> topics = new HashSet<Topic>(); // 包含多少主题
	private int topicCount; // 主题的数量 ：实时更新主题的数量
	private int artricleCount; // 文章的数量 [主题Topic + 回复Reply] ： 实时更新文章的数量
	private Topic lastTopic; // 最后发布的主题 ：实时更新

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public int getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	public int getArtricleCount() {
		return artricleCount;
	}

	public void setArtricleCount(int artricleCount) {
		this.artricleCount = artricleCount;
	}

	public Topic getLastTopic() {
		return lastTopic;
	}

	public void setLastTopic(Topic lastTopic) {
		this.lastTopic = lastTopic;
	}

}
