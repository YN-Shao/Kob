package com.kob.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GomokuRecord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer aId;
    private Integer bId;
    private String steps;
    private String loser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+10")
    private Date createTime;

    // No argument constructor
    public GomokuRecord() {
    }

    // All arguments constructor
    public GomokuRecord(Integer id, Integer aId, Integer bId, String steps, String loser, Date createTime) {
        this.id = id;
        this.aId = aId;
        this.bId = bId;
        this.steps = steps;
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
    public String getSteps() {
        return this.steps;
    }


}
