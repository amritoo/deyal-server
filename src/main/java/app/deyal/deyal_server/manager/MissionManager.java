package app.deyal.deyal_server.manager;

import app.deyal.deyal_server.dao.MissionRepository;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.Mission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

    public List<Mission> findAllMissionsSorted() {
        List<Mission> missions = findAllMissions();

        List<Mission> assigned = new ArrayList<>();
        List<Mission> notAssigned = new ArrayList<>();
        for (Mission mission : missions) {
            if (mission.getContractorId() != null)
                assigned.add(mission);
            else notAssigned.add(mission);
        }
        assigned.sort(Comparator.comparing(Mission::getTitle));
        notAssigned.addAll(assigned);
        return notAssigned;
    }

    public List<Mission> findMyMissions(ArrayList<String> missionIds) throws ApiError {
        List<Mission> missions = new ArrayList<>();
        for (String missionId : missionIds) {
            try {
                Mission mission = retrieveMissionById(missionId);
                missions.add(mission);
            } catch (ApiError ignored) {
            }
        }
        return missions;
    }

    public Mission retrieveMissionById(String missionId) throws ApiError {
        Optional<Mission> entity = missionRepository.findById(missionId);
        if (!entity.isPresent()) {
            throw ApiError.NOT_FOUND;
        }

        return entity.get();
    }

    public List<Mission> retrieveMissionByTitle(String title) throws ApiError {
        Mission probe = new Mission();
        probe.setTitle(title);
        List<Mission> entity = missionRepository.findAll(Example.of(probe), Sort.by(Sort.Direction.ASC, "id"));
        if (entity.isEmpty()) {
            throw ApiError.NOT_FOUND;
        }

        return entity;
    }

    public List<Mission> retrieveMission(String creatorId, String title) throws ApiError {
        List<Mission> missions = missionRepository.findByCreatorIdAndTitle(creatorId, title);
        if (missions.isEmpty()) {
            throw ApiError.NOT_FOUND;
        }

        return missions;
    }
}
