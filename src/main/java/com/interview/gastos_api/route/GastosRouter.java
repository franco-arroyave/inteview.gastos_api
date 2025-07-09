package com.interview.gastos_api.route;

import com.interview.gastos_api.handler.GastosHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class GastosRouter {

    @Bean
    public RouterFunction<ServerResponse> Gastos(GastosHandler gastosHandler){
        return RouterFunctions
                .route(GET("/gastosTotal"), gastosHandler::totalGastos)
                .andRoute(GET("/empleados/", gastosHandler::listarGastos)
                .andRoute(GET("/empleados/{id}"), gastosHandler::gastosEmpleado)
                ;
    }

}
