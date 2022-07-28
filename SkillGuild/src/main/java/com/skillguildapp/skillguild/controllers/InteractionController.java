package com.skillguildapp.skillguild.controllers;

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

import com.skillguildapp.skillguild.entities.Interaction;
import com.skillguildapp.skillguild.services.ContentService;
import com.skillguildapp.skillguild.services.InteractionService;
import com.skillguildapp.skillguild.services.UserService;

@RestController
@CrossOrigin({ "*", "http://localhost" })
@RequestMapping("v1")
public class InteractionController {

	@Autowired
	InteractionService intServ;
	@Autowired
	ContentService conServ;

	@Autowired
	UserService userServ;
	

	@GetMapping("interactions")
	public List<Interaction> index() {
		return intServ.index();
	}


//	@GetMapping("contents/{cid}/interactions")
//	public List<Interaction> showContentInteractions (HttpServletRequest req, HttpServletResponse res, @PathVariable int cid) {
//
//		List<Interaction> interactions = intServ.showContentInteractions(cid);
//
//		if (interactions.isEmpty()) {
//			res.setStatus(404);
//		}
//
//		return interactions;
//	}
//
//	@GetMapping("users/{uid}/interactions")
//	public List<Interaction> showUserInteractions (HttpServletRequest req, HttpServletResponse res, @PathVariable int uid) {
//		List<Interaction> interactions = intServ.showUserInteractions(uid);
//		if (interactions == null) {
//			res.setStatus(404);
//		}
//		return interactions;
//	}

	@PostMapping("users/{uid}/content/{cid}/interactions")
	public Interaction create(HttpServletRequest req, HttpServletResponse res, @PathVariable int uid, @PathVariable int cid,
			@RequestBody Interaction interaction) {

		try {
			intServ.create(uid, cid, interaction);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL().append("/").append(interaction.getId());
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);

		}

		return interaction;

	}

	@PutMapping("interactions/{iid}")
	public Interaction update(HttpServletRequest req, HttpServletResponse res, @PathVariable int iid,
			@RequestBody Interaction interaction) {
		Interaction newInteraction;
		try {
			newInteraction = intServ.update(iid, interaction);
			if (newInteraction == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			newInteraction = null;
		}

		return newInteraction;

	}

	@DeleteMapping("interactions/{iid}")
	public void delete(HttpServletRequest req, HttpServletResponse res, @PathVariable int iid) {

		try {
			if (intServ.delete(iid)) {
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
