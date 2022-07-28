package com.skillguildapp.skillguild.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "interaction_type")
public class InteractionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	@Column(name = "img_url")
	private String imgUrl;

	public InteractionType() {
	}

	@JsonIgnore
	@OneToMany(mappedBy = "interactionType")
	private List<Interaction> interactions;

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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public List<Interaction> getInteractions() {
		return interactions;
	}

	public void setInteractions(List<Interaction> interactions) {
		this.interactions = interactions;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InteractionType other = (InteractionType) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InteractionType [id=").append(id).append(", name=").append(name).append(", imgUrl=")
				.append(imgUrl).append("]");
		return builder.toString();
	}

}
