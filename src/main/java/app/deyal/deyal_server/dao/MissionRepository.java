package app.deyal.deyal_server.dao;

import app.deyal.deyal_server.model.Mission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MissionRepository extends MongoRepository<Mission, String> {

    List<Mission> findByCreatorIdAndTitle(String creatorId, String title);

}
