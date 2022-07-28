package com.skillguildapp.skillguild.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private String description;

	@Column(name = "img_url")
	private String imgUrl;
	
	@JsonIgnoreProperties({"categories"})
	@ManyToMany(mappedBy = "categories")
	private List<Guild> guilds;

	
	public void addGuild(Guild guild) {
		if (guild == null)
			guilds = new ArrayList<>();

		if (!guilds.contains(guild)) {
			guilds.add(guild);
			guild.addCategories(this);
		}
	}

	public void removeGuild(Guild guild) {
		if (guilds != null && guilds.contains(guild)) {
			guilds.remove(guild);
			guild.removeCategories(this);
		}
		
	}
	
	public Category() {
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<Guild> getGuilds() {
		return guilds;
	}

	public void setGuilds(List<Guild> guilds) {
		this.guilds = guilds;
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
		Category other = (Category) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Category [id=").append(id).append(", name=").append(name).append(", description=")
				.append(description).append(", imgUrl=").append(imgUrl).append("]");
		return builder.toString();
	}

}
