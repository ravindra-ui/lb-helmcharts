package com.lats.service;

import com.lats.entity.Instance;
import com.lats.repository.InstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstanceService {

    @Autowired
    private InstanceRepository instanceRepository;

    public Instance getInstance(String emailId, String accountType, String instanceId) {

        List<Instance> instanceList = instanceRepository.getInstance(emailId, accountType);

        System.out.println("---------------");
        System.out.println("");
        System.out.println("instanceId :");
        System.out.println(instanceId);
        System.out.println("");
        System.out.println("GET INSTANCE LIST");
        System.out.println("");
        System.out.println(instanceList);
        System.out.println("");
        System.out.println("---------------");

        if (instanceList.size() < 1) {
            return null;
        }


        for (Instance instance : instanceList) {

            if (instanceId.equals(instance.getInstance_id())) {

                System.out.println("=============");
                System.out.println("");
                System.out.println("instance found for instanceId : " + instanceId);
                System.out.println("");
                System.out.println("=============");

                return instance;
            }

        }

        return instanceList.get(instanceList.size() - 1);

    }


}
