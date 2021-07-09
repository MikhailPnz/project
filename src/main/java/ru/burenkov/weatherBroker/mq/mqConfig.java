package ru.burenkov.weatherBroker.mq;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"mq", "pub-sub", "publish-subscribe"})
@Configuration
public class mqConfig {
}
