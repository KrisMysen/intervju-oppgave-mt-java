package no.mattilsynet.kodeoppgave.java.intervjuoppgavemtjava;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StuffController.class)
@Import({SecurityConfig.class, StuffService.class})
class StuffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCountWordsOfSpecifiedLength() throws Exception {
        StuffDto stuff = new StuffDto("a b c a", 1);

        String result =
            mockMvc.perform(
                post("/stuff")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(stuff))
                )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("{\"a\":2,\"b\":1,\"c\":1}", result);
    }

    @Test
    void shouldNotCountWordsOfSpecifiedLength() throws Exception {
        var stuff = new StuffDto("a b c a", 2);

        String result =
            mockMvc.perform(
                post("/stuff")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(stuff))
                )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("{}", result);
    }

    @Test
    void shouldIgnoreSigns() throws Exception {
        var stuff = new StuffDto("hello there, my guy", 5);

        String result =
            mockMvc.perform(
                post("/stuff")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(stuff))
                )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("{\"hello\":1,\"there\":1}", result);
    }

    @Test
    void shouldIgnoreCapitalization() throws Exception {
        var stuff = new StuffDto("Hello hello", 5);
        String result =
            mockMvc.perform(
                post("/stuff")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(stuff))
                )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals("{\"hello\":2}", result);
    }

    @Test
    @Disabled
    @Timeout(value = 5)
    void gottaGoFast() throws Exception {
        long start = System.currentTimeMillis();

        var stuff = new StuffDto(entireBible(), 1);
        mockMvc.perform(
            post("/stuff")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(stuff))
            )
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        long end = System.currentTimeMillis();
        System.out.println("Took " + (end - start) + "ms");
    }

    private String entireBible() throws IOException {
        return Files.readString(new File("src/test/resources/bible.txt").toPath());
    }
}