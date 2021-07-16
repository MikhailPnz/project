package ru.burenkov.weatherBroker.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import ru.burenkov.weatherBroker.utils.MappingUtils;

@Controller
//@EnableScheduling
public class mqAppController {

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
    public mqReceiver receiver() { return new mqReceiver(); }

    @Bean
    public mqSender sender() {
        return new mqSender();
    }
}
