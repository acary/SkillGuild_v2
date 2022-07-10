package com.skilldistillery.skillguild.controllers;

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

import com.skilldistillery.skillguild.entities.Content;
import com.skilldistillery.skillguild.services.ContentService;
import com.skilldistillery.skillguild.services.GuildService;

@RestController
@CrossOrigin({ "*", "http://localhost" })
@RequestMapping("v1")
public class ContentController {

	@Autowired
	ContentService contentServ;

	@Autowired
	GuildService guildServ;

	@GetMapping("contents")
	public List<Content> index() {
		return contentServ.index();
	}

	@GetMapping("contents/{cid}")
	public Content show(HttpServletRequest req, HttpServletResponse res, @PathVariable int cid) {

		Content content = contentServ.show(cid);

		if (content == null) {
			res.setStatus(404);
		}

		return content;
	}

	@GetMapping("guilds/{gid}/contents")
	public List<Content> guildContent(HttpServletRequest req, HttpServletResponse res, @PathVariable int gid) {

		List<Content> contents = contentServ.guildContents(gid);

		if (contents.isEmpty()) {
			res.setStatus(404);
		}

		return contents;
	}

	@GetMapping("users/contents")
	public List<Content> userContent(Principal principal, HttpServletRequest req, HttpServletResponse res) {

		List<Content> contents = contentServ.userContents(principal.getName());

		if (contents.isEmpty()) {
			res.setStatus(404);
		}
		return contents;
	}
	
	@GetMapping("guilds/{gid}/contents/{cid}")
	public Content showGuildContent(HttpServletRequest req, HttpServletResponse res, @PathVariable int gid,
			@PathVariable int cid) {

		Content content = contentServ.showGuildContent(gid, cid);

		if (content == null) {
			res.setStatus(404);
		}

		return content;
	}

	@GetMapping("users/{uid}/contents/{cid}")
	public Content showUserContent(HttpServletRequest req, HttpServletResponse res, @PathVariable int uid,
			@PathVariable int cid) {

		Content content = contentServ.showGuildContent(uid, cid);

		if (content == null) {
			res.setStatus(404);
		}

		return content;
	}
	
	
	@PostMapping("users/{uid}/guilds/{gid}/statuses/{sid}/contents")
	public Content create(HttpServletRequest req, HttpServletResponse res, @PathVariable int uid, @PathVariable int gid,
			@PathVariable int sid, @RequestBody Content content) {

		try {
			contentServ.create(uid, gid, sid, content);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL().append("/").append(content.getId());
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);

		}

		return content;

	}
	
	@PostMapping("guilds/{gid}/statuses/{sid}/contents")
	public Content createNewContent(Principal principal, HttpServletRequest req, HttpServletResponse res, @PathVariable int gid,
			@PathVariable int sid, @RequestBody Content content) {
		
		try {
			contentServ.createNewContent(gid, sid, content, principal.getName());
			res.setStatus(201);
			StringBuffer url = req.getRequestURL().append("/").append(content.getId());
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			
		}
		
		return content;
		
	}

	@PutMapping("contents/{cid}")
	public Content update(HttpServletRequest req, HttpServletResponse res, @PathVariable int cid,
			@RequestBody Content content) {

		Content newContent;
		try {
			newContent = contentServ.update(cid, content);
			if (newContent == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			newContent = null;
		}

		return newContent;

	}

	@DeleteMapping("contents/{cid}")
	public void delete(HttpServletRequest req, HttpServletResponse res, @PathVariable int cid) {

		try {
			if (contentServ.delete(cid)) {

				res.setStatus(204);

			} else {

				res.setStatus(404);

			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}

	}

}
