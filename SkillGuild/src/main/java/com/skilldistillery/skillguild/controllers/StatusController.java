package com.skilldistillery.skillguild.controllers;

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

import com.skilldistillery.skillguild.entities.Status;
import com.skilldistillery.skillguild.services.StatusService;

@RestController
@CrossOrigin({ "*", "http://localhost" })
@RequestMapping("v1")
public class StatusController {

	@Autowired
	StatusService statusServ;

	@GetMapping("statuses")
	public List<Status> index() {
		return statusServ.index();
	}

	@GetMapping("statuses/{sid}")
	public Status show(HttpServletRequest req, HttpServletResponse res, @PathVariable int sid) {

		Status status = statusServ.show(sid);

		if (status == null) {
			res.setStatus(404);
		}
		return status;
	}

	@PostMapping("statuses")
	public Status create(HttpServletRequest req, HttpServletResponse res, @RequestBody Status status) {

		try {
			statusServ.create(status);
			res.setStatus(201);
			StringBuffer url = req.getRequestURL();
			res.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}
		return status;
	}
	
	@DeleteMapping("statuses/{sid}")
	public boolean deleteStatus(@PathVariable Integer sid, HttpServletResponse res) {
		if (statusServ.delete(sid)) {
			res.setStatus(204);
			return true;
		} else {
			res.setStatus(404);
			return false;
		}
	}

	@PutMapping("statuses/{sid}")
	public Status updateStatus(
			// Principal principal,
			@PathVariable("sid") int sid, @RequestBody Status status, HttpServletResponse res) {
		try {
			status = statusServ.update(sid, status);
			if (status == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			status = null;
		}
		return status;
	}

}
