package com.tecumsofia.carrito.controller;
import com.tecumsofia.carrito.controller.PagoController;
import com.tecumsofia.carrito.service.PagoService;
import com.tecumsofia.carrito.view.PagoResumenView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PagoControllerTest {

        @InjectMocks
        private PagoController pagoController;

        @Mock
        private PagoService pagoService;

        private MockMvc mockMvc;

        @Before
        public void setup() {
            mockMvc = MockMvcBuilders.standaloneSetup(pagoController).build();
        }

        @Test
        public void testRealizarPago_exitoso() throws Exception {
            PagoResumenView resumen = new PagoResumenView(1L, "123", new BigDecimal("350.0"), "efectivo", "aprobado", LocalDateTime.now());

            when(pagoService.realizarPagoConResumen("123", "efectivo")).thenReturn(resumen);

            String json = "{ \"codUsuario\": \"123\", \"metodo\": \"efectivo\" }";

            mockMvc.perform(post("/api/pago")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.codUsuario").value("123"))
                    .andExpect(jsonPath("$.metodo").value("efectivo"))
                    .andExpect(jsonPath("$.estado").value("aprobado"));
        }

}