package app.deyal.deyal_server.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;

public class Mission {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(required = true, position = 0)
    private String title;
    @ApiModelProperty(required = true, position = 1)
    private String description;
    @ApiModelProperty(required = true, position = 2)
    private String longDescription;
    @ApiModelProperty(required = true, position = 3)
    private MissionDifficulty difficulty;

    @ApiModelProperty(hidden = true)
    private String creatorId;
    @ApiModelProperty(hidden = true)
    private String creatorName;

    @ApiModelProperty(hidden = true)
    private String contractorId;
    @ApiModelProperty(hidden = true)
    private String contractorName;

    public Mission() {
    }

    public Mission(String title, String description, String longDescription, MissionDifficulty difficulty) {
        this.title = title;
        this.description = description;
        this.longDescription = longDescription;
        this.difficulty = difficulty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public MissionDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(MissionDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

}
