package com.skillguildapp.skillguild.services;

import java.util.List;

import com.skillguildapp.skillguild.entities.Status;

public interface StatusService {

	List<Status> index();

	Status show(int sid);

	Status create(Status status);

	boolean delete(int sid);

	Status update(int sid, Status status);

}
