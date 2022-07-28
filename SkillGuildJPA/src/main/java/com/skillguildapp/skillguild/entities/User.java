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
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "about_me")
	private String aboutMe;

	private boolean enabled;

	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	@Column(name = "profile_img_url")
	private String profileImgUrl;

	private String role;

	private String username;

	@CreationTimestamp
	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@UpdateTimestamp
	@Column(name = "last_updated")
	private LocalDateTime lastUpdated;

	@JsonIgnore
	@OneToMany(mappedBy = "userCreatedBy")
	private List<Guild> guildsCreated;

	@JsonIgnore
	@OneToMany(mappedBy = "userCreatedContent")
	private List<Content> contents;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Interaction> interactions;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Member> members;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
	private List<Comment> comments;

	// methods
	public User() {
	}

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfileImgUrl() {
		return profileImgUrl;
	}

	public void setProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public List<Interaction> getInteractions() {
		return interactions;
	}

	public void setInteractions(List<Interaction> interactions) {
		this.interactions = interactions;
	}

	public List<Guild> getGuildsCreated() {
		return guildsCreated;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public void setGuildsCreated(List<Guild> guildsCreated) {
		this.guildsCreated = guildsCreated;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
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
		User other = (User) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", username=").append(username).append(", password=")
				.append(password).append(", email=").append(email).append(", enabled=").append(enabled)
				.append(", role=").append(role).append(", createdOn=").append(createdOn).append(", lastUpdated=")
				.append(lastUpdated).append(", firstName=").append(firstName).append(", lastName=").append(lastName)
				.append(", profileImgUrl=").append(profileImgUrl).append(", aboutMe=").append(aboutMe).append("]");
		return builder.toString();
	}

}
