package com.dashu.log.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/8/28 下午2:39
 **/
@Entity
@Table(name = "error_log_type", schema = "logMonitor")
public class ErrorLogType {
    private int id;
    private String businessId;
    private String businessName;
    private String logLevel;
    private String category;
    private String keywords;
    private Timestamp createTime;
    private Timestamp lastUpdatetime;
    private String message;
    private String hostName;
    private String source;
    private String alterDesc;
    private Integer isForbid;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "business_id")
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Basic
    @Column(name = "business_name")
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Basic
    @Column(name = "log_level")
    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    @Basic
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "last_updatetime")
    public Timestamp getLastUpdatetime() {
        return lastUpdatetime;
    }

    public void setLastUpdatetime(Timestamp lastUpdatetime) {
        this.lastUpdatetime = lastUpdatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorLogType that = (ErrorLogType) o;
        return id == that.id &&
                businessId == that.businessId &&
                Objects.equals(businessName, that.businessName) &&
                Objects.equals(logLevel, that.logLevel) &&
                Objects.equals(category, that.category) &&
                Objects.equals(keywords, that.keywords) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(lastUpdatetime, that.lastUpdatetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, businessId, businessName, logLevel, category, keywords, createTime, lastUpdatetime);
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "host_name")
    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Basic
    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "alter_desc")
    public String getAlterDesc() {
        return alterDesc;
    }

    public void setAlterDesc(String alterDesc) {
        this.alterDesc = alterDesc;
    }

    @Basic
    @Column(name = "is_forbid")
    public Integer getIsForbid() {
        return isForbid;
    }

    public void setIsForbid(Integer isForbid) {
        this.isForbid = isForbid;
    }
}
