package app.deyal.deyal_server.dao;

import app.deyal.deyal_server.model.OTPData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OTPRepository extends MongoRepository<OTPData, String> {

    Optional<OTPData> findByEmail(String email);

}
