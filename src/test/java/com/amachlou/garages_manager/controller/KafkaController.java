package com.amachlou.garages_manager.controller;

import com.amachlou.garages_manager.model.Vehicule;
import com.amachlou.garages_manager.repository.VehiculeRepository;
import com.amachlou.garages_manager.service.VehiculeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
//@RequestMapping("/api/kafka")
public class KafkaController {

    @Autowired
    private KafkaProducer producer;
    @Autowired
    private KafkaConsumer consumer;
    @Autowired
    private VehiculeService vehiculeService;
    @Autowired
    private VehiculeRepository vehiculeRepository;

    @GetMapping("/publish")
    public ResponseEntity<String> publish() {
        System.out.println("-------------------------------------> ");

        vehiculeRepository.findAll().forEach(vehicule -> producer.sendMessage(vehicule));

        return ResponseEntity.ok("Message sent to Kafka");
    }

    @GetMapping("/consume")
    public List<Vehicule> consume() {
//        return ResponseEntity.ok("Message sent to Kafka");
        return consumer.getReceivedVehicules();
    }

    @GetMapping("/send")
    public Object publishVehiculw() {
        System.out.println("-------------------------------------> ");
        producer.sendMessage(vehiculeService.findByGarageId(1L).get(0));
//        return "Message sent to Kafka";
        return vehiculeRepository.findById(1L);
    }

    @GetMapping("/receive")
    @ResponseBody
    public List<Vehicule> consumeVehiculw() {
//        return ResponseEntity.ok("Message sent to Kafka");
        return consumer.getReceivedVehicules();
    }
}

@Service
class KafkaConsumer {

    private final List<Vehicule> receivedVehicules = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "garage-topic", groupId = "vehicule-group")
    public void consume(Vehicule vehicule) {
        System.out.println("Consumed message: ");
        receivedVehicules.add(vehicule);
    }

    public List<Vehicule> getReceivedVehicules() {
        return receivedVehicules;
    }
}

@Service
@AllArgsConstructor
class KafkaProducer {

    //    @Value("${kafka.topic.name}")
    private static String TOPIC = "garage-topic";

    private KafkaTemplate<String, Vehicule> kafkaTemplate;
    private VehiculeService vehiculeService;
    private VehiculeRepository vehiculeRepository;

    public void sendMessage(Vehicule vehicule) {
        kafkaTemplate.send(TOPIC, vehicule);
        System.out.println("Produced message: ");
    }
}