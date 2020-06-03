package org.fasttrackit.motocamp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyJsonMapper extends ObjectMapper {

        public MyJsonMapper() {
            this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        }
}

