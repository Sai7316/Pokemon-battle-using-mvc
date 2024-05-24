package com.impact.pokemon;
import com.fasterxml.jackson.core.type.TypeReference;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class PokemonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAttackEndpointWithParams() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/attack")
                .param("pokemonA", "Illumise")
                .param("pokemonB", "Charmeleon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.winner").exists())
                .andExpect(jsonPath("$.hitPoints").exists())
                .andReturn();   

   

        String responseContent = mvcResult.getResponse().getContentAsString();
     

        ObjectMapper objectMapper = new ObjectMapper();


        Map<String, Object> resultMap = objectMapper.readValue(responseContent, new TypeReference<Map<String, Object>>() {});


        assertEquals(2, resultMap.size());
        assertEquals("Charmeleon", resultMap.get("winner"));
        assertEquals(58, resultMap.get("hitPoints"));

     }
}




