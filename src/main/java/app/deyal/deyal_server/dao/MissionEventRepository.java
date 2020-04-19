package app.deyal.deyal_server.dao;

import app.deyal.deyal_server.model.MissionEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MissionEventRepository extends MongoRepository<MissionEvent, String> {

}
