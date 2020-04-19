package app.deyal.deyal_server.manager;

import app.deyal.deyal_server.dao.MissionRepository;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissionManager {

    @Autowired
    private MissionRepository missionRepository;

    public List<Mission> findAllMissions() {
        return missionRepository.findAll();
    }

    public void deleteMission(Mission mission) {
        missionRepository.delete(mission);
    }

    public void createMission(Mission mission) {
        missionRepository.insert(mission);
    }

    public void updateMission(Mission mission) {
        missionRepository.save(mission);
    }

    public Mission retrieveMissionById(String missionId) throws ApiError {
        Optional<Mission> entity = missionRepository.findById(missionId);
        if (!entity.isPresent()) {
            throw ApiError.NOT_FOUND;
        }

        return entity.get();
    }

    public List<Mission> retrieveMission(String creatorId, String title) throws ApiError {
        List<Mission> missions = missionRepository.findByCreatorIdAndTitle(creatorId, title);
        if (missions.isEmpty()) {
            throw ApiError.NOT_FOUND;
        }

        return missions;
    }
}
