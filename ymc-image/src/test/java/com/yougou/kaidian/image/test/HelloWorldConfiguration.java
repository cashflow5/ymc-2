package com.yougou.kaidian.image.test;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  
public class HelloWorldConfiguration {  
  
    protected final String helloWorldQueueName = "ymc.handleimage.batch.queue";
  
    @Bean
    public ConnectionFactory connectionFactory() {  
    	CachingConnectionFactory connectionFactory = new CachingConnectionFactory(  
                "10.0.30.187");  
        connectionFactory.setUsername("guest");  
        connectionFactory.setPassword("guest");  
        return connectionFactory;  
    }  
  
    @Bean
    public RabbitTemplate rabbitTemplate() {  
        RabbitTemplate template = new RabbitTemplate(connectionFactory());  
        // The routing key is set to the name of the queue by the broker for the  
        // default exchange.  
        template.setRoutingKey(this.helloWorldQueueName);  
        // // Where we will synchronously receive messages from  
        template.setQueue(this.helloWorldQueueName);
        JsonMessageConverter messageConverter = new JsonMessageConverter();
        messageConverter.setClassMapper(new DefaultClassMapper());
        template.setMessageConverter(messageConverter);
        return template;  
    }  
  
    @Bean  
    public Queue helloWorldQueue() {  
        return new Queue(this.helloWorldQueueName);  
    }  
}  