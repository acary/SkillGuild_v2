package com.skillguildapp.skillguild.services;

import java.util.List;

import com.skillguildapp.skillguild.entities.Content;

public interface ContentService {

	List<Content> index();
	
	List<Content> guildContents(int gid);

	Content show(int cid);

	Content create(int uid, int gid, int sid, Content content);
	Content createNewContent(int gid, int sid, Content content, String username);
	
	Content update(int cid, Content content);
	
	boolean delete(int cid);
	
	Content showGuildContent(int gid, int cid);

	List<Content> userContents(String username);

	Content showUserContent(int uid, int cid);
	
	
}
