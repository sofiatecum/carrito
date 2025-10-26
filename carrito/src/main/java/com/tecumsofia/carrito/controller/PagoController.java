package com.tecumsofia.carrito.controller;

import com.tecumsofia.carrito.service.PagoService;
import com.tecumsofia.carrito.view.PagoResumenView;
import com.tecumsofia.carrito.view.SolicitudPagoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pago")
public class PagoController {

        private final PagoService pagoService;

        public PagoController(PagoService pagoService) {
            this.pagoService = pagoService;
        }

        @PostMapping
        public ResponseEntity<PagoResumenView> realizarPago(@RequestBody SolicitudPagoRequest request) {
            try {
                PagoResumenView resumen = pagoService.realizarPagoConResumen(
                        request.getCodUsuario(),
                        request.getMetodo()
                );
                return ResponseEntity.ok(resumen);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
}
