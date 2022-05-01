package com.lats.repository;

import com.lats.entity.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InstanceRepository {

    @Autowired
    private CassandraOperations cassandraOperations;

    private static final Class ENTITY_CLASS = Instance.class;


    public List<Instance> getInstance(String emailId, String accountType) {

        Query query = Query.query(Criteria.where("email_id").is(emailId)).and(Criteria.where("account_type").is(accountType));
        return cassandraOperations.select(query, ENTITY_CLASS);
    }


}
