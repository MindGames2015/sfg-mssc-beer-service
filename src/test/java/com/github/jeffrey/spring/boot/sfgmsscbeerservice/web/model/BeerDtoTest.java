package com.github.jeffrey.spring.boot.sfgmsscbeerservice.web.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

/**
 * Created by jeffreymzd on 3/17/20
 */
@JsonTest
class BeerDtoTest extends BaseTest{

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testSerializeDto() throws JsonProcessingException {

        String jsonString = objectMapper.writeValueAsString(getDto());

        System.out.println(jsonString);
    }

    @Test
    void testDeserializeJsonString() throws JsonProcessingException {

        String jsonString = "{\"id\":\"ea7e4087-fd1f-49cc-afba-ade7f27e55a8\",\"version\":null,\"createdDate\":\"2020-03-17T13:34:59.929729-04:00\",\"lastModifiedDate\":\"2020-03-17T13:34:59.930801-04:00\",\"beerName\":\"Galaxy\",\"beerStyle\":\"PALE_ALE\",\"upc\":123456789012,\"price\":12.949999999999999289457264239899814128875732421875,\"quantityOnHand\":null}";

        BeerDto beerDto = objectMapper.readValue(jsonString, BeerDto.class);

        System.out.println(beerDto);
    }

    @Test
    void testDeserializeJsonString_withLastRestockDate() {

        String jsonString = "{\"id\":\"07490e79-33aa-4f88-9398-ce90554c7770\",\"version\":null,\"createdDate\":\"2020-03-17T15:25:45.732796-04:00\",\"lastModifiedDate\":\"2020-03-17T15:25:45.733672-04:00\",\"beerName\":\"Galaxy\",\"beerStyle\":\"PALE_ALE\",\"upc\":123456789012,\"price\":\"12.949999999999999289457264239899814128875732421875\",\"quantityOnHand\":null,\"lastRestockDate\":\"2020-03-17\"}";

        // Exception thrown as custom serdes used for lastRestockDate
        Assertions.assertThrows(JsonMappingException.class, () -> {
            objectMapper.readValue(jsonString, BeerDto.class);
        });
    }

    @Test
    void testDeserializeJsonString_withLastRestockDate_byLocalDateSerializer() throws JsonProcessingException {

        String jsonString = "{\"id\":\"7d5e2bff-8ea4-40d2-986a-98391de58d4c\",\"version\":null,\"createdDate\":\"2020-03-17T15:28:45.425431-04:00\",\"lastModifiedDate\":\"2020-03-17T15:28:45.426327-04:00\",\"beerName\":\"Galaxy\",\"beerStyle\":\"PALE_ALE\",\"upc\":123456789012,\"price\":\"12.949999999999999289457264239899814128875732421875\",\"quantityOnHand\":null,\"lastRestockDate\":\"20200317\"}";

        BeerDto beerDto = objectMapper.readValue(jsonString, BeerDto.class);

        System.out.println(beerDto);
    }

    @Test
    void testSnakeDesirialize_snakeNamingStrategy() throws JsonProcessingException {

        String jsonString = "{\"id\":\"579e61cd-295b-44f3-a2d4-5225eb63b077\",\"version\":null,\"created_date\":\"2020-03-17T13:51:58.385235-04:00\",\"last_modified_date\":\"2020-03-17T13:51:58.385999-04:00\",\"beer_name\":\"Galaxy\",\"beer_style\":\"PALE_ALE\",\"upc\":123456789012,\"price\":12.949999999999999289457264239899814128875732421875,\"quantity_on_hand\":null}";

        BeerDto dto = objectMapper.readValue(jsonString, BeerDto.class);

        System.out.println(dto);
    }

    @Test
    void testSnakeDesirialize_kebabtNamingStrategy() throws JsonProcessingException {

        String jsonString = "{\"id\":\"46894952-cc50-4860-b68b-b3e3716accc1\",\"version\":null,\"created-date\":\"2020-03-17T13:58:40.463939-04:00\",\"last-modified-date\":\"2020-03-17T13:58:40.464936-04:00\",\"beer-name\":\"Galaxy\",\"beer-style\":\"PALE_ALE\",\"upc\":123456789012,\"price\":12.949999999999999289457264239899814128875732421875,\"quantity-on-hand\":null}";

        BeerDto dto = objectMapper.readValue(jsonString, BeerDto.class);

        System.out.println(dto);
    }

}