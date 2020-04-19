package app.deyal.deyal_server.manager;

import app.deyal.deyal_server.dao.MissionEventRepository;
import app.deyal.deyal_server.model.MissionEvent;
import app.deyal.deyal_server.model.events.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionEventManager {

    @Autowired
    private MissionEventRepository missionEventRepository;

    public List<MissionEvent> findAllMissionEvents(String missionId) {
//        add();
        MissionEvent probe = new MissionEvent();
        probe.setMission_id(missionId);
        List<MissionEvent> entity = missionEventRepository.findAll(Example.of(probe), Sort.by(Sort.Direction.ASC, "eventTime"));
        return entity;
    }

    private void add() {
        missionEventRepository.insert(new MissionEvent(EventType.CREATE));
        missionEventRepository.insert(new MissionEvent(EventType.UPDATE));
        missionEventRepository.insert(new MissionEvent(EventType.UPDATE));
        missionEventRepository.insert(new MissionEvent(EventType.UPDATE));
        missionEventRepository.insert(new MissionEvent(EventType.PUBLISH));
        missionEventRepository.insert(new MissionEvent(EventType.REQUEST));
        missionEventRepository.insert(new MissionEvent(EventType.REQUEST));
        missionEventRepository.insert(new MissionEvent(EventType.REQUEST));
        missionEventRepository.insert(new MissionEvent(EventType.REQUEST));
        missionEventRepository.insert(new MissionEvent(EventType.ASSIGN));
        missionEventRepository.insert(new MissionEvent(EventType.SUBMIT));
        missionEventRepository.insert(new MissionEvent(EventType.APPROVE));
        missionEventRepository.insert(new MissionEvent(EventType.REVIEW));

        missionEventRepository.insert(new MissionEvent(EventType.CREATE));
        missionEventRepository.insert(new MissionEvent(EventType.UPDATE));
        missionEventRepository.insert(new MissionEvent(EventType.UPDATE));
        missionEventRepository.insert(new MissionEvent(EventType.UPDATE));
        missionEventRepository.insert(new MissionEvent(EventType.PUBLISH));
        missionEventRepository.insert(new MissionEvent(EventType.REQUEST));
        missionEventRepository.insert(new MissionEvent(EventType.ASSIGN));
        missionEventRepository.insert(new MissionEvent(EventType.SUBMIT));
        missionEventRepository.insert(new MissionEvent(EventType.REJECT));
        missionEventRepository.insert(new MissionEvent(EventType.REVIEW));
    }
}
