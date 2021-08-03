package ru.burenkov.weatherBroker.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.burenkov.weatherBroker.mq.MqReceiver;
import ru.burenkov.weatherBroker.mq.MqSender;

@Configuration
public class MqConfig {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }

    @Bean
    public Queue autoDeleteQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(FanoutExchange fanout, Queue autoDeleteQueue) {
        return BindingBuilder.bind(autoDeleteQueue).to(fanout);
    }

    @Bean
    public MqReceiver receiver() { return new MqReceiver(); }

    @Bean
    public MqSender sender() {
        return new MqSender();
    }

}
