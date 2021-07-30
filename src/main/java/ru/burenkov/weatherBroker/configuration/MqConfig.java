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
    public Queue autoDeleteQueue1() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding1(FanoutExchange fanout, Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
    }

    @Bean
    public MqReceiver receiver() { return new MqReceiver(); }

    @Bean
    public MqSender sender() {
        return new MqSender();
    }

}
