package com.skillguildapp.skillguild.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillguildapp.skillguild.entities.Guild;
import com.skillguildapp.skillguild.entities.Member;
import com.skillguildapp.skillguild.services.GuildService;

@RestController
@CrossOrigin({ "*", "http://localhost" })
@RequestMapping("v1")
public class GuildController {

	@Autowired
	GuildService guildServ;

	@GetMapping("guilds")
	public List<Guild> index() {
		return guildServ.index();
	}

	@GetMapping("guilds/{gid}")
	public Guild show(HttpServletRequest req, HttpServletResponse res, @PathVariable int gid) {

		Guild guild = guildServ.show(gid);

		if (guild == null) {
			res.setStatus(404);
		}

		return guild;
	}

	@GetMapping("guilds/myguilds")
	public List<Guild> memberOfGuild(HttpServletRequest req, HttpServletResponse res, Principal principal) {

		List<Guild> guilds = guildServ.memberOfGuild(principal.getName());

		if (guilds == null) {
			res.setStatus(404);
		}

		return guilds;
	}
	
	@GetMapping("guilds/{gid}/members")
	public List<Member> getGuildMembers(Principal principal, @PathVariable int gid, HttpServletRequest req, HttpServletResponse res) {
		
		List<Member> members = guildServ.getGuildMembers(gid);

		if (members == null) {
			res.setStatus(404);
		}

		return members;
	}

//	@PostMapping("users/{uid}/guilds")
//	public Guild create(HttpServletRequest req, HttpServletResponse res, @PathVariable int uid,
//			@RequestBody Guild guild) {
//		try {
//			guildServ.create(uid, guild);
//			res.setStatus(201);
//			StringBuffer url = req.getRequestURL().append("/").append(guild.getId());
//			res.setHeader("Location", url.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//			res.setStatus(400);
//
//		}
//		return guild;
//	}
	
	@PostMapping("guilds")
	public Guild create(Principal principal, HttpServletRequest req, HttpServletResponse res, @RequestBody Guild guild) {
		try {
			guildServ.create(guild, principal.getName());
			res.setStatus(201);
			StringBuffer url = req.getRequestURL().append("/").append(guild.getId());
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			
		}
		return guild;
	}

	@PutMapping("guilds/{gid}")
	public Guild update(Principal principal, HttpServletRequest req, HttpServletResponse res, @PathVariable int gid,
			@RequestBody Guild guild) {

		try {
			guild = guildServ.update(gid, guild);
			if (guild == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			res.setStatus(400);
			guild = null;
		}

		return guild;

	}

	@DeleteMapping("guilds/{gid}")
	public void delete(HttpServletRequest req, HttpServletResponse res, @PathVariable int gid) {

		try {
			if (guildServ.delete(gid)) {

				res.setStatus(204);

			} else {

				res.setStatus(404);

			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}
	}

	@PostMapping("guilds/{gid}/{uid}")
	public boolean joinGuild(HttpServletRequest req, HttpServletResponse res, @PathVariable int uid,
			@PathVariable int gid) {
		try {
			guildServ.join(uid, gid);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL().append("/").append(gid);
			res.setHeader("Location", url.toString());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}
		return false;
	}

}
