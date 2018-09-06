package com.dashu.log.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @Description
 * @Author: xuyouchang
 * @Date 2018/9/6 上午10:20
 **/
@Entity
@Table(name = "filter_error_type", schema = "logMonitor")
public class FilterErrorType {
    private int id;
    private Integer errorTypeId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "error_type_id")
    public Integer getErrorTypeId() {
        return errorTypeId;
    }

    public void setErrorTypeId(Integer errorTypeId) {
        this.errorTypeId = errorTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterErrorType that = (FilterErrorType) o;
        return id == that.id &&
                Objects.equals(errorTypeId, that.errorTypeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, errorTypeId);
    }
}
