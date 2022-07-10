package com.skilldistillery.skillguild.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Guild {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String description;

	@JsonProperty(value="isPublic") 
	@Column(name = "is_public")
	private boolean isPublic;

	@Column(name = "cover_img")
	private String coverImg;

	@Column(name = "membership_criteria")
	private String membershipCriteria;

	@CreationTimestamp
	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@UpdateTimestamp
	@Column(name = "last_updated")
	private LocalDateTime lastUpdated;

	@JsonIgnoreProperties({"guild"})
	@OneToMany(mappedBy = "guild", cascade = { CascadeType.REMOVE })
	private List<Content> contents;

	@JsonIgnore
	@OneToMany(mappedBy = "guild", cascade = { CascadeType.REMOVE })
	private List<Member> members;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "group_category", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "guild_id"))
	private List<Category> categories;

	@ManyToOne
	@JoinColumn(name = "created_by_user_id")
	@JsonProperty(value="createdByUser") 
	private User userCreatedBy;

	// Methods

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	public void addCategories(Category category) {
		if (category == null)
			categories = new ArrayList<>();

		if (!categories.contains(category)) {
			categories.add(category);
			category.addGuild(this);
		}
	}

	public void removeCategories(Category category) {
		if (categories != null && categories.contains(category)) {
			categories.remove(category);
			category.removeGuild(this);
		}
	}

	public Guild() {
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getMembershipCriteria() {
		return membershipCriteria;
	}

	public void setMembershipCriteria(String membershipCriteria) {
		this.membershipCriteria = membershipCriteria;
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

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

//	public List<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}

	public User getUserCreatedBy() {
		return userCreatedBy;
	}

	public void setUserCreatedBy(User userCreatedBy) {
		this.userCreatedBy = userCreatedBy;
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
		Guild other = (Guild) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Guild [id=").append(id).append(", name=").append(name).append(", description=")
				.append(description).append(", isPublic=").append(isPublic).append(", coverImg=").append(coverImg)
				.append(", membershipCriteria=").append(membershipCriteria).append(", createdOn=").append(createdOn)
				.append(", lastUpdated=").append(lastUpdated).append("]");
		return builder.toString();
	}

}
