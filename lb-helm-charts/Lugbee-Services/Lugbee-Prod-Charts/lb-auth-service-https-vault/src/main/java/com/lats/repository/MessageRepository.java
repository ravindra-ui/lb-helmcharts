package com.lats.repository;

import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MessageRepository {


    @Autowired
    private CassandraTemplate cassandraTemplate;


    //Get Validation Messages from Message Library
    public Map<Integer, Map<String, Object>> getMessages(Integer[] messageCodeList) {

        //creating selection criteria
        String delimiter = ",";
        StringBuilder sb = new StringBuilder();
        for (int element : messageCodeList) {
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(element);
        }
        String messageCodes = sb.toString();

        //Create CQL Query
        String getMessageQuery = "select * from messages where message_code IN(" + messageCodes + ")";

        //Execute CQL Query
        ResultSet resultSet = cassandraTemplate.getCqlOperations().queryForResultSet(getMessageQuery);

        //call convertResultSetToMap method for convert ResultSet into Map
        return convertResultSetToMap(resultSet);
    }


    //Produce a Map of Validation Messages From ResultSet
    public Map<Integer, Map<String, Object>> convertResultSetToMap(ResultSet resultSet) {

        //getting all the elements of the resultSet
        List<Row> rowList = resultSet.all();
        int rowListSize = rowList.size();

        //checking for data accessed or not
        if (rowListSize == 0) {
            return null;
        }

        //Creating Map for setting Key and Value of Data
        Map<Integer, Map<String, Object>> stringObjectMap = new HashMap<Integer, Map<String, Object>>(rowListSize);

        //Creating Object of ColumnDefinitions
        ColumnDefinitions columnDefinitions = resultSet.getColumnDefinitions();
        int columnCount = columnDefinitions.size();

        //getting column name and value
        for (int i = 0; i < rowListSize; i++) {

            Map<String, Object> stringObjectMap1 = new HashMap<String, Object>(rowListSize);


            for (int j = 0; j < columnCount; j++) {

                //Getting Column Name of Table
                String column = columnDefinitions.getName(j);

                //Getting Column value from RowList
                Object column_value = rowList.get(i).getObject(column);

                //putting Column and its value into the Value Map Object
                stringObjectMap1.put(column, column_value);

            }

            int message_code = rowList.get(i).getInt("message_code");

            //Putting the Message Code as a key and Value Map Object as a value
            stringObjectMap.put(message_code, stringObjectMap1);

        }

        System.out.println("-------------");
        System.out.println("");
        System.out.println("Message Object Map");
        System.out.println(stringObjectMap);
        System.out.println("");
        System.out.println("-------------");

        //return the Map Object
        return stringObjectMap;
    }



}
