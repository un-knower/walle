package com.dashu.log.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/11/26 上午10:48
 **/
@Entity
@Table(name = "index_conf", schema = "logmonitor")
public class IndexConf {
    private int id;
    private String index;
    private String filed;
    private String keywords;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "index")
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Basic
    @Column(name = "filed")
    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    @Basic
    @Column(name = "keywords")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexConf indexConf = (IndexConf) o;
        return id == indexConf.id &&
                Objects.equals(index, indexConf.index) &&
                Objects.equals(filed, indexConf.filed) &&
                Objects.equals(keywords, indexConf.keywords);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, index, filed, keywords);
    }
}
