package com.skillguildapp.skillguild.services;

import java.util.List;

import com.skillguildapp.skillguild.entities.Guild;
import com.skillguildapp.skillguild.entities.Member;

public interface GuildService {
	
	List<Guild> index();
	List<Guild> memberOfGuild(String username);
	List<Member> getGuildMembers(int gid);
	Guild show(int gid);
	Guild create(Guild guild, String username);
	Guild update(int gid, Guild guild);
	boolean delete(int gid);
	boolean join(int gid, int uid);

}
