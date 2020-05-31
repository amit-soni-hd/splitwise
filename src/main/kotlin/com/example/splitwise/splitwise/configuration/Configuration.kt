package com.example.splitwise.splitwise.configuration

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {

    /**
     * create the bean of model mapper for mapping
     */
    @Bean
    fun getModelMapper(): ModelMapper {
        return ModelMapper()
    }
}