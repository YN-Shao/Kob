package com.kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class snakeRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer aId;
    private Integer aSx;
    private Integer aSy;
    private Integer bId;
    private Integer bSx;
    private Integer bSy;
    private String aSteps;
    private String bSteps;
    private String map;
    private String loser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+10")
    private Date createTime;

    // No argument constructor
    public snakeRecord() {
    }

    // All arguments constructor
    public snakeRecord(Integer id, Integer aId, Integer aSx, Integer aSy, Integer bId, Integer bSx, Integer bSy, String aSteps, String bSteps, String map, String loser, Date createTime) {
        this.id = id;
        this.aId = aId;
        this.aSx = aSx;
        this.aSy = aSy;
        this.bId = bId;
        this.bSx = bSx;
        this.bSy = bSy;
        this.aSteps = aSteps;
        this.bSteps = bSteps;
        this.map = map;
        this.loser = loser;
        this.createTime = createTime;
    }

    // Getters and Setters for all fields
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // ... the same for other fields ...
    // for example:

    public Integer getAId() {
        return this.aId;
    }

    public void setAId(Integer aId) {
        this.aId = aId;
    }
    public Integer getBId() {
        return this.bId;
    }
    public String getLoser() {
        return this.loser;
    }
    public void setLoser(String loser) {
        this.loser = loser;
    }
    public String getMap() {
        return this.map;
    }
    public void setMap(String map) {
        this.map = map;
    }
    public Integer getASx() {
        return this.aSx;
    }
    public Integer getASy() {
        return this.aSy;
    }
    public Integer getBSx() {
        return this.bSx;
    }
    public Integer getBSy() {
        return this.bSy;
    }
    public String getASteps() {
        return this.aSteps;
    }
    public String getBSteps() {
        return this.bSteps;
    }

}
