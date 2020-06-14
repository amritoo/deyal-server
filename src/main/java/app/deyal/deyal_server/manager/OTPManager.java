package app.deyal.deyal_server.manager;

import app.deyal.deyal_server.dao.OTPRepository;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.OTPData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OTPManager {

    @Autowired
    private OTPRepository otpRepository;

    public void deleteOTPData(OTPData otpData) {
        otpRepository.delete(otpData);
    }

    public void saveOTPData(OTPData otpData) {
        otpRepository.save(otpData);
    }

    public OTPData retrieveOTPDataByEmail(String email) throws ApiError {
        Optional<OTPData> entity = otpRepository.findByEmail(email);
        if (!entity.isPresent()) {
            throw ApiError.NOT_FOUND;
        }
        return entity.get();
    }

    public boolean isEmailExist(String email) {
        Optional<OTPData> entity = otpRepository.findByEmail(email);
        return entity.isPresent();
    }

}
