package avengers.nexus.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class LongListConverter implements AttributeConverter<List<Long>,String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Long> dataList){
        try {
            return objectMapper.writeValueAsString(dataList);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> convertToEntityAttribute(String data) {
        try {
            return objectMapper.readValue(data,List.class);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}


