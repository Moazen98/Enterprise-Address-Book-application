package com.example.demo.MessageQueue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQDirectConfig {

	@Bean
	Queue ITQueue() {
		return new Queue("ITQueue", false);
	}

	@Bean
	Queue NetworkingQueue() {
		return new Queue("NetworkingQueue", false);
	}

	@Bean
	Queue ProgrammingQueue() {
		return new Queue("ProgrammingQueue", false);
	}

	@Bean
	DirectExchange exchange2() {
		return new DirectExchange("direct-exchange");  //the name of exchange2
	}

	@Bean
	Binding ITBinding(Queue ITQueue, DirectExchange exchange2) {
		return BindingBuilder.bind(ITQueue).to(exchange2).with("IT"); //with : is the routingkey name
	}

	@Bean
	Binding NetworkingBinding(Queue NetworkingQueue, DirectExchange exchange2) {
		return BindingBuilder.bind(NetworkingQueue).to(exchange2).with("Networking");
	}

	@Bean
	Binding ProgrammingBinding(Queue ProgrammingQueue, DirectExchange exchange2) {
		return BindingBuilder.bind(ProgrammingQueue).to(exchange2).with("Programming");
	}

}