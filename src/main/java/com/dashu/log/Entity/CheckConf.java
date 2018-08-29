package com.dashu.log.Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/8/28 下午2:39
 **/
@Entity
@Table(name = "check_conf", schema = "logMonitor")
public class CheckConf {
    private int id;
    private String md5;
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
    @Column(name = "md5")
    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
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
        CheckConf checkConf = (CheckConf) o;
        return id == checkConf.id &&
                Objects.equals(md5, checkConf.md5) &&
                Objects.equals(createTime, checkConf.createTime) &&
                Objects.equals(lastUpdatetime, checkConf.lastUpdatetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, md5, createTime, lastUpdatetime);
    }
}
