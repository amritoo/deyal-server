package app.deyal.deyal_server.model;

import app.deyal.deyal_server.model.events.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

public class MissionEvent {
    @Id
    private String id;

    private EventType eventType;
    private String missionId;
    private Date eventTime;
    private String username;

    //creation depends on eventType
    private Create create;
    private Update update;
    private Publish publish;
    private Request request;
    private Assign assign;
    private Submit submit;
    private Approve approve;
    private Reject reject;
    private Review review;

    public MissionEvent() {
    }

    public MissionEvent(String missionId, EventType eventType, String username) {
        this.eventType = eventType;
        this.missionId = missionId;
        this.eventTime = new Date();
        this.username = username;
    }

    public void create(String createdBy) {
        this.create = new Create();
        this.create.setCreatedBy(createdBy);
    }

    public void request(String requestBy, String message) {
        this.request = new Request();
        this.request.setRequestBy(requestBy);
        this.request.setRequestMessage(message);
    }

    public void assign(String assignTo, String message) {
        this.assign = new Assign();
        this.assign.setAssignTo(assignTo);
        this.assign.setAssignMessage(message);
    }

    public void submit(String proof) {
        this.submit = new Submit();
        this.submit.setProofOfWork(proof);
    }

    public void approve(String message) {
        this.approve = new Approve();
        this.approve.setApproveMessage(message);
    }

    public void reject(String message) {
        this.reject = new Reject();
        this.reject.setRejectMessage(message);
    }

    public void review(boolean gotReward, String message) {
        this.review = new Review();
        this.review.setGotReward(gotReward);
        this.review.setMessage(message);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Approve getApprove() {
        return approve;
    }

    public void setApprove(Approve approve) {
        this.approve = approve;
    }

    public Assign getAssign() {
        return assign;
    }

    public void setAssign(Assign assign) {
        this.assign = assign;
    }

    public Create getCreate() {
        return create;
    }

    public void setCreate(Create create) {
        this.create = create;
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public Publish getPublish() {
        return publish;
    }

    public void setPublish(Publish publish) {
        this.publish = publish;
    }

    public Reject getReject() {
        return reject;
    }

    public void setReject(Reject reject) {
        this.reject = reject;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Submit getSubmit() {
        return submit;
    }

    public void setSubmit(Submit submit) {
        this.submit = submit;
    }
}
