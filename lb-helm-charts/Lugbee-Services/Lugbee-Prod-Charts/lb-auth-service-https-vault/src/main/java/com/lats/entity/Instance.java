package com.lats.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Table(value = "instance")
public class Instance {
    @PrimaryKeyColumn(name = "instance_id", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private String instance_id;
    @PrimaryKeyColumn(name = "email_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String email_id;
    private long creation_ts;
    private UUID account_id;
    @PrimaryKeyColumn(name = "account_type", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String account_type;



    public long getCreation_ts() {
        return creation_ts;
    }

    public void setCreation_ts(long creation_ts) {
        this.creation_ts = creation_ts;
    }

    public String getInstance_id() {
        return instance_id;
    }

    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public UUID getAccount_id() {
        return account_id;
    }

    public void setAccount_id(UUID account_id) {
        this.account_id = account_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }


}


//    PRIMARY KEY  (  email_id, user_type)
//    @PrimaryKeyColumn(name = "actor_id",ordinal = 0,type = PrimaryKeyType.PARTITIONED)
//    ordinal -> The order of this column relative to other primary key columns.


