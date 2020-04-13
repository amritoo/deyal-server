package app.deyal.deyal_server.dao;

import app.deyal.deyal_server.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
