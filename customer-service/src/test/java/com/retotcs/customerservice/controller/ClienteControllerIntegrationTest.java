package com.retotcs.customerservice.controller;
import com.retotcs.customerservice.persistence.entities.Cliente;
import com.retotcs.customerservice.persistence.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ClienteControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ClienteRepository clienteRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Inicializa MockMvc manualmente a partir del contexto de la aplicación
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        clienteRepository.deleteAll();
    }

    @Test
    void testObtenerClientesIntegration() throws Exception {
        mockMvc.perform(get("/clientes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}