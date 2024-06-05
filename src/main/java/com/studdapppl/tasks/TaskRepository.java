package com.studdapppl.tasks;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends MongoRepository<Task, ObjectId> {
}
