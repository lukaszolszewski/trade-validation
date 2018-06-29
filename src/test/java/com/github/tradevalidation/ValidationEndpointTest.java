package com.github.tradevalidation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tradevalidation.api.ValidationEndpoint;
import com.github.tradevalidation.model.TradeInformation;
import com.github.tradevalidation.model.ValidationResponse;
import com.github.tradevalidation.service.ValidationService;
import com.google.common.util.concurrent.Futures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Future;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ValidationEndpoint.class, secure = false)
public class ValidationEndpointTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ValidationService validationService;

    @Test
    public void validateTradeInformationTest() throws Exception {
        //Given
        TradeInformation trade = TradeInformation.builder()
                .amount1(10.0f)
                .amount2(10.2f)
                .valueDate("2016-08-15")
                .tradeDate("2016-08-11").build();

        Future<Set<ValidationResponse>> feature = Futures.immediateFuture(Collections.singleton(ValidationResponse
                .builder()
                .tradeInformation(trade).build()));

        //when
        when(validationService.validateTrades(Collections.singleton(trade)))
                .thenReturn(feature);

        this.mvc.perform(
        post("/validate")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(Collections.singleton(trade))))

        //then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].tradeInformation.valueDate", equalTo("2016-08-15")))
        .andExpect(jsonPath("$[0].tradeInformation.tradeDate", equalTo("2016-08-11")))
        .andExpect(jsonPath("$[0].tradeInformation.amount1", equalTo(10.0)))
        .andExpect(jsonPath("$[0].tradeInformation.amount2", equalTo(10.2)));
    }
}
