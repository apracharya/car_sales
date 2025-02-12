package com.apr.car_sales.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    // not sure why this is here.
    public EnvConfig() {
        Dotenv dotenv = Dotenv.load();
    }
}
