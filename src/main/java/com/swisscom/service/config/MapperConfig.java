package com.swisscom.service.config;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.dateFormat(new StdDateFormat().withColonInTimeZone(true));
//            builder.serializerByType(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
            builder.deserializers(new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
            builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        };
    }
}
