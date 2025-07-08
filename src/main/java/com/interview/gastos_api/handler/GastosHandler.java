package com.interview.gastos_api.handler;

import com.interview.gastos_api.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class GastosHandler {
    @Autowired
    private DataService dataService;

    public Mono<ServerResponse> getGastos(ServerRequest request){
        return dataService.getGastos()
                .collectList()
                .flatMap(gastos -> {
                    if(gastos.isEmpty()){
                        return ServerResponse.noContent().build();
                    }
                    return ServerResponse.ok().bodyValue(gastos);
                });
    }

    public Mono<ServerResponse> getGastosEmpleado(ServerRequest request){
        Long id = castID(request);
        if(id == -1L){
            return ServerResponse.badRequest().bodyValue("ID inválido: Debe ser de tipo numerico");
        }
        return dataService.getGastosPorEmpleado(id)
                .collectList()
                .flatMap(gastos -> {
                    if(gastos.isEmpty()){
                        return ServerResponse.noContent().build();
                    }
                    return ServerResponse.ok().bodyValue(gastos);
                });

    }

    private Long castID(ServerRequest request) {
        try {
            return Long.parseLong(request.pathVariable("id"));
        } catch (NumberFormatException e){
            return -1L;
        }
    }
}
