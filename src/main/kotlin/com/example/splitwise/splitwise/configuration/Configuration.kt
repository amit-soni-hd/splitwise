package com.example.splitwise.splitwise.configuration

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {

    @Bean
    fun getModelMapper(): ModelMapper {
        return ModelMapper()
    }
}