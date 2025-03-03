package com.openclassrooms.mddapi.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for ModelMapper.
 * This class sets up the ModelMapper bean, which is used to map objects between different data types or layers.
 */
@Configuration // Marks this class as a configuration class that can be used by Spring to manage beans.
public class ModelMapperConfig {

    /**
     * Creates a ModelMapper bean to be used for object mapping.
     * 
     * @return A ModelMapper instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper(); // Returns a new instance of ModelMapper to be managed by Spring.
    }
}
