package com.dashu.log.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/26 下午5:32
 **/
@Entity
@Table(name = "logstash_conf", schema = "logmonitor")
public class LogstashConf {
    private int id;
    private String hostname;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "hostname")
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogstashConf that = (LogstashConf) o;
        return id == that.id &&
                Objects.equals(hostname, that.hostname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, hostname);
    }
}
