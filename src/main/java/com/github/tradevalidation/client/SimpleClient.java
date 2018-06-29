package com.github.tradevalidation.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.tradevalidation.model.ValidationResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.IOException;
import java.util.Set;

public class SimpleClient {

    public static JsonNode getRequestBody() throws IOException {
        File file = ResourceUtils.getFile("classpath:sample-trade-data.json");
        return JsonLoader.fromFile(file);
    }

    public static void main(String[] args) throws IOException, UnirestException {

        HttpResponse<com.mashape.unirest.http.JsonNode> response = Unirest.post("http://localhost:8080/validate")
                .header("content-type","application/json")
                .header("accept","application/json")
                .body(getRequestBody().toString())
                .asJson();


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Set<ValidationResponse> result = objectMapper.readValue(response.getBody().toString(), new TypeReference<Set<ValidationResponse>>(){});


        result.stream().forEach(validationResponse -> {
            System.out.println(validationResponse.tradeInformation);
            validationResponse.errors.stream().forEach(System.out::println);
            System.out.println("====");
        });
    }
}
