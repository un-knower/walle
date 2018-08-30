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
@Table(name = "query_history", schema = "logMonitor")
public class QueryHistory {
    private int id;
    private String indexName;
    private String oldestTime;
    private Timestamp createTime;
    private Timestamp lastUpdatetime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "index_name")
    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    @Basic
    @Column(name = "oldest_time")
    public String getOldestTime() {
        return oldestTime;
    }

    public void setOldestTime(String oldestTime) {
        this.oldestTime = oldestTime;
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
        QueryHistory that = (QueryHistory) o;
        return id == that.id &&
                Objects.equals(indexName, that.indexName) &&
                Objects.equals(oldestTime, that.oldestTime) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(lastUpdatetime, that.lastUpdatetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, indexName, oldestTime, createTime, lastUpdatetime);
    }
}
