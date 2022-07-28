package com.skillguildapp.skillguild.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillguildapp.skillguild.entities.Guild;
import com.skillguildapp.skillguild.entities.Member;
import com.skillguildapp.skillguild.entities.MemberId;
import com.skillguildapp.skillguild.entities.User;
import com.skillguildapp.skillguild.repositories.GuildRepository;
import com.skillguildapp.skillguild.repositories.MemberRepository;
import com.skillguildapp.skillguild.repositories.UserRepository;

@Service
public class GuildServiceImpl implements GuildService {

	@Autowired
	private GuildRepository guildRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private MemberRepository memberRepo;

	@Override
	public List<Guild> index() {
		return guildRepo.findAll();
	}

	@Override
	public Guild show(int gid) {

		Optional<Guild> op = guildRepo.findById(gid);
		if (op.isPresent()) {
			Guild result = op.get();
			return result;
		}

		return null;
	}

	@Override
	public List<Guild> memberOfGuild(String username) {

		User user = userRepo.findByUsername(username);

		if (user != null) {

			List<Guild> guildList = guildRepo.findByMembers_User(user);
			if (guildList != null) {
				return guildList;
			}
		}

		return null;
	}

	@Override
	public Guild create(Guild guild, String username) {

		User user = userRepo.findByUsername(username);
		
		if (user != null) {
			
			guild.setUserCreatedBy(user);

			guildRepo.saveAndFlush(guild);

			MemberId memberId = new MemberId(guild.getId(), user.getId());

			Member member = new Member();
			member.setId(memberId);
			member.setGuild(guild);
			member.setUser(user);
			member.setApprovedBy(user.getId());
			member.setModerator(true);

			memberRepo.save(member);

			return guild;
		}

		return null;

	}

	@Override
	public Guild update(int gid, Guild guild) {

		Optional<Guild> op = guildRepo.findById(gid);
		if (op.isPresent()) {
			Guild result = op.get();
			result = guild;
			result.setId(gid);
			result.setUserCreatedBy(guild.getUserCreatedBy());
			return guildRepo.saveAndFlush(result);
		}

		return null;
	}

	@Override
	public boolean delete(int gid) {

		Optional<Guild> op = guildRepo.findById(gid);
		if (op.isPresent()) {
			guildRepo.deleteById(gid);
			op = guildRepo.findById(gid);
			return !op.isPresent();

		}
		return false;
	}

	@Override
	public boolean join(int gid, int uid) {
		Optional<User> op = userRepo.findById(uid);
		if (op.isPresent()) {
			User user = op.get();
			Optional<Guild> guildOp = guildRepo.findById(gid);
			if (guildOp.isPresent()) {
				Guild guild = guildOp.get();
				MemberId memberId = new MemberId(guild.getId(), user.getId());
				Member member = new Member();
				member.setId(memberId);
				member.setGuild(guild);
				member.setUser(user);
				member.setApprovedBy(uid);
				member.setModerator(false);
				memberRepo.save(member);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Member> getGuildMembers(int gid) {
		return memberRepo.findByGuild_id(gid);
	}

}
