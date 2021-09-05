package app.deyal.deyal_server.manager;

import app.deyal.deyal_server.dao.MissionEventRepository;
import app.deyal.deyal_server.model.ApiError;
import app.deyal.deyal_server.model.MissionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissionEventManager {

    @Autowired
    private MissionEventRepository missionEventRepository;

    public void deleteEvent(MissionEvent missionEvent) {
        missionEventRepository.delete(missionEvent);
    }

    public MissionEvent findById(String eventId) throws ApiError {
        Optional<MissionEvent> entity = missionEventRepository.findById(eventId);
        if (!entity.isPresent())
            throw ApiError.NOT_FOUND;
        return entity.get();
    }

    public List<MissionEvent> findAllMissionEvents(String missionId) throws ApiError {
        MissionEvent probe = new MissionEvent();
        probe.setMissionId(missionId);
        List<MissionEvent> entity = missionEventRepository.findAll(Example.of(probe), Sort.by(Sort.Direction.ASC, "eventTime"));
        if (entity.isEmpty()) {
            throw ApiError.NOT_FOUND;
        }
        return entity;
    }

    public void addEvent(MissionEvent missionEvent) {
        missionEventRepository.insert(missionEvent);
    }

}
