package com.amachlou.garages_manager.service.kafka;

import com.amachlou.garages_manager.model.Vehicule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class VehiculeProducer {

    private static final String TOPIC = "vehicule-topic";

    @Autowired
    private KafkaTemplate<String, Vehicule> vehiculeKafkaTemplate;

    public void publish(Vehicule vehicule) {
        vehiculeKafkaTemplate.send(TOPIC, vehicule.getId().toString(), vehicule);
    }

}
