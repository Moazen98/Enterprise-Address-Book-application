package com.example.demo.MessageQueue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.modal.User;

// this class will send the message to the exchane rabbit

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${javainuse.rabbitmq.exchange}") //for application.properties value
	private String exchange;
	
	@Value("${javainuse.rabbitmq.routingkey}") //for application.properties value
	private String routingkey;	
	
	public void send(User user) {   // here I send the message
		rabbitTemplate.convertAndSend(exchange, routingkey, user);
		System.out.println("The Send Message is : " + user);
	    
	}
}
