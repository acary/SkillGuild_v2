package com.skillguildapp.skillguild.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Content {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;
	private String description;

	@Column(name = "publish_date")
	private LocalDateTime publishDate;

	@JsonProperty(value="isPublic") 
	@Column(name = "is_public")
	private boolean isPublic;

	@Column(name = "is_live")
	private boolean isLive;

	@UpdateTimestamp
	@Column(name = "last_updated")
	private LocalDateTime lastUpdated;

	@Column(name = "length_minutes")
	private Integer lengthMinutes;

	@Column(name = "presentation_date")
	private LocalDateTime presentationDate;

	@ManyToOne
	@JoinColumn(name = "guild_id")
	private Guild guild;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "created_by_user_id")
	@JsonProperty(value="createdByUser") 
	private User userCreatedContent;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;

//	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "content_resource", joinColumns = @JoinColumn(name = "content_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
	private List<Resource> resources;

//	@JsonIgnore
	@OneToMany(mappedBy = "content")
	private List<Question> questions;

//	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "content_topic", joinColumns = @JoinColumn(name = "content_id"), inverseJoinColumns = @JoinColumn(name = "topic_id"))
	private List<Topic> topics;

	@JsonIgnore
	@OneToMany(mappedBy = "content")
	private List<Comment> comments;

	@JsonIgnore
	@OneToMany(mappedBy = "content")
	private List<Interaction> interactions;

	public Guild getGuild() {
		return guild;
	}

	public void setGuild(Guild guild) {
		this.guild = guild;
	}

	public User getUserCreatedContent() {
		return userCreatedContent;
	}

	public void setUserCreatedContent(User userCreatedContent) {
		this.userCreatedContent = userCreatedContent;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Interaction> getInteractions() {
		return interactions;
	}

	public void setInteractions(List<Interaction> interactions) {
		this.interactions = interactions;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	// Methods

	public Content() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(LocalDateTime publishDate) {
		this.publishDate = publishDate;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Integer getLengthMinutes() {
		return lengthMinutes;
	}

	public void setLengthMinutes(Integer lengthMinutes) {
		this.lengthMinutes = lengthMinutes;
	}

	public LocalDateTime getPresentationDate() {
		return presentationDate;
	}

	public void setPresentationDate(LocalDateTime presentationDate) {
		this.presentationDate = presentationDate;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
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
		Content other = (Content) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Content [id=" + id + ", title=" + title + ", description=" + description + ", publishDate="
				+ publishDate + ", isPublic=" + isPublic + ", isLive=" + isLive + ", lastUpdated=" + lastUpdated
				+ ", lengthMinutes=" + lengthMinutes + ", presentationDate=" + presentationDate + ", guild=" + guild
				+ ", userCreatedContent=" + userCreatedContent + ", status=" + status + ", topics=" + topics
				+ ", comments=" + comments + ", interactions=" + interactions + ", resources=" + resources
				+ ", questions=" + questions + "]";
	}

}
