package com.skillguildapp.skillguild.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "in_reply_to_id")
	private Comment commentInReplyTo;

	@Column(name = "text_content")
	private String textContent;

	@JsonProperty(value="hasBeenEdited") 
	@Column(name = "has_been_edited")
	private boolean edited;

	@CreationTimestamp
	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@UpdateTimestamp
	@Column(name = "last_updated")
	private LocalDateTime lastUpdated;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonProperty(value="createdByUser")
	private User user;

	@ManyToOne
	@JoinColumn(name = "content_id")
	private Content content;

	public Comment() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Comment getCommentInReplyTo() {
		return commentInReplyTo;
	}

	public void setCommentInReplyTo(Comment commentInReplyTo) {
		this.commentInReplyTo = commentInReplyTo;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Comment [id=").append(id).append(", commentInReplyTo=").append(commentInReplyTo)
				.append(", textContent=").append(textContent).append(", edited=").append(edited).append(", createdOn=")
				.append(createdOn).append(", lastUpdated=").append(lastUpdated).append(", user=").append(user)
				.append(", content=").append(content).append("]");
		return builder.toString();
	}

}
