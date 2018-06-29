package com.github.tradevalidation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.tradevalidation.api.ValidationEndpoint;
import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidationIntegrationTest {

    @Autowired
    private ValidationEndpoint validationEndpoint;

    private JsonNode jsonRequestBody;

    @Before
    public void setUp() throws IOException {
        File file = ResourceUtils.getFile("classpath:sample-trade-data.json");
        jsonRequestBody = JsonLoader.fromFile(file);
    }

    @Test
    public void validationTest() throws IOException {
        //Given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Set<TradeInformation> tradeInformationSet = objectMapper.readValue(jsonRequestBody.toString(), new TypeReference<Set<TradeInformation>>(){});

        //When
        ResponseEntity<Set<ValidationResponse>> response = validationEndpoint.validateTradeInformation(tradeInformationSet);

        //Then
        assertThat(response, notNullValue());
        assertThat(response.getBody().size(), equalTo(15));
    }

}
