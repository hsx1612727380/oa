package com.hsx.oa.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Topic extends Article implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 普通帖
	public static final int TYPE_NORMAL = 0;
	// 精华帖
	public static final int TYPE_BEST = 1;
	// 置顶帖
	public static final int TYPE_TOP = 2;

	private int type; // 类型[普通帖、精华帖、置顶帖]
	private int replyCount; // 回复数量 ： 实时更新回复数
	private Reply lastReply; // 最后的回复 ： 实时更新最后的回复
	private Date lastUpdateTime; // 最后更新时间 ：记录回复的时间，要是没有回复，就记录主题发布的时间
	private Forum forum; // 所属的板块
	private Set<Reply> replies = new HashSet<Reply>(); // 包含多少回复

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public Reply getLastReply() {
		return lastReply;
	}

	public void setLastReply(Reply lastReply) {
		this.lastReply = lastReply;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public Set<Reply> getReplies() {
		return replies;
	}

	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}

}
