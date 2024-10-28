package com.kjo.talkpost.exception.moc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void testGlobalException400() throws Exception {
    mockMvc.perform(get("/api/test/exception/400")).andExpect(jsonPath("$.errorCode").value("400"));
  }
}
