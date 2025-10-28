package org.example.solvroapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.solvroapi.Entity.KoktajlEntity;
import org.example.solvroapi.repositories.KocktajlsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class KoktailsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KocktajlsRepo kocktajlsRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private KoktajlEntity sampleCocktail;

    @BeforeEach
    void setUp() {
        sampleCocktail = KoktajlEntity.builder()
                .name("Mojito")
                .category("Classic")
                .instruction("Mix ingredients and serve cold.")
                .build();
        kocktajlsRepo.save(sampleCocktail);
    }

    @Test
    void shouldCreateCocktail() throws Exception {
        KoktajlEntity newCocktail = KoktajlEntity.builder()
                .name("Pina Colada")
                .category("Tropical")
                .instruction("Blend all ingredients until smooth.")
                .build();

        mockMvc.perform(post("/cocktails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCocktail)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Pina Colada"));
    }

    @Test
    void shouldGetAllCocktails() throws Exception {
        mockMvc.perform(get("/cocktails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", notNullValue()));
    }

    @Test
    void shouldGetCocktailById() throws Exception {
        mockMvc.perform(get("/cocktails/{id}", sampleCocktail.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mojito"));
    }

    @Test
    void shouldUpdateCocktail() throws Exception {
        KoktajlEntity updated = KoktajlEntity.builder()
                .name("Updated Mojito")
                .category("Updated")
                .instruction("Shake and serve with ice.")
                .build();

        mockMvc.perform(put("/cocktails/{id}", sampleCocktail.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Mojito"))
                .andExpect(jsonPath("$.category").value("Updated"));
    }

    @Test
    void shouldDeleteCocktail() throws Exception {
        mockMvc.perform(delete("/cocktails/{id}", sampleCocktail.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/cocktails/{id}", sampleCocktail.getId()))
                .andExpect(status().isNotFound());
    }
}
