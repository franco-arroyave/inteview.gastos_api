package com.interview.gastos_api.handler;

import com.interview.gastos_api.model.*;
import com.interview.gastos_api.service.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GastosHandlerTest {

    @MockitoBean
    private DataService dataService;
    private GastosHandler gastosHandler;

    @BeforeEach
    void setup(){
        dataService = mock(DataService.class);
        gastosHandler = new GastosHandler(dataService);
    }

    @Test
    void listarGastos_ShouldReturnGroupedAndProcessedGastos() {
        List<Gasto> gastos = List.of(
                new Gasto(1L, 1L, LocalDate.of(2023, 9, 1), 200_000.0),
                new Gasto(2L, 1L, LocalDate.of(2023, 9, 15), 300_000.0),
                new Gasto(3L, 2L, LocalDate.of(2023, 10, 1), 500_000.0)
        );

        when(dataService.getGastos()).thenReturn(Flux.fromIterable(gastos));

        when(dataService.getEmpleado(1L)).thenReturn(Mono.just(new Empleado(1L, "Juan Pérez")));
        when(dataService.getEmpleado(2L)).thenReturn(Mono.just(new Empleado(2L, "Ana Gómez")));

        ServerRequest request = mock(ServerRequest.class);

        Mono<ServerResponse> responseMono = gastosHandler.listarGastos(request);

        StepVerifier.create(responseMono)
                .expectNextMatches(response -> {
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    void gastosEmpleado_ShouldReturnGastosForSpecificEmployee() {
        List<Gasto> gastos = List.of(
                new Gasto(1L, 1L, LocalDate.of(2023, 9, 1), 200_000.0),
                new Gasto(2L, 1L, LocalDate.of(2023, 9, 15), 300_000.0)
        );
        when(dataService.getGastosPorEmpleado(1L)).thenReturn(Flux.fromIterable(gastos));

        ServerRequest request = mock(ServerRequest.class);
        when(request.pathVariable("id")).thenReturn("1");

        Mono<ServerResponse> responseMono = gastosHandler.gastosEmpleado(request);

        StepVerifier.create(responseMono)
                .expectNextMatches(response -> {
                    assertEquals(MediaType.APPLICATION_JSON, response.headers().getContentType());
                    return true;
                })
                .verifyComplete();
    }
}