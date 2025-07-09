package com.interview.gastos_api.router;

import com.interview.gastos_api.handler.GastosHandler;
import com.interview.gastos_api.model.Gasto;
import com.interview.gastos_api.service.DataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Nested
class GastosRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private DataService dataService;

    @BeforeEach
    void setup(){
        dataService = mock(DataService.class);
        GastosHandler gastosHandler = new GastosHandler(dataService);
        GastosRouter gastosRouter = new GastosRouter();
        webTestClient = WebTestClient.bindToRouterFunction(gastosRouter.Gastos(gastosHandler)).build();
    }

    @Test
    void retornarGastosEmpleado() {
        List<Gasto> gastos = List.of(
                new Gasto(1L, 1L, LocalDate.of(2023, 9, 1), 200_000.0)
        );
        when(dataService.getGastosPorEmpleado(1L)).thenReturn(Flux.fromIterable(gastos));


        webTestClient.get()
                .uri("/empleados/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].monto").isEqualTo(200_000);
    }

}


