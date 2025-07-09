package com.interview.gastos_api.handler;

import com.interview.gastos_api.dto.GastoResumenDTO;
import com.interview.gastos_api.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.interview.gastos_api.model.Empleado;
import com.interview.gastos_api.model.Gasto;

import java.time.YearMonth;
import java.util.Comparator;
import java.util.Map;

@Component
public class GastosHandler {
    @Autowired
    private DataService dataService;

    public Mono<ServerResponse> totalGastos(ServerRequest request) {
        return dataService.getGastos()
                .reduce(0.0,(total,gasto)-> total + gasto.getMonto())
                .flatMap(total -> {
                    Map<String, Double> response = Map.of("totalGastos",total);
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(response);
                })
                .switchIfEmpty(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse.badRequest().
                        bodyValue(Map.of("Mensaje","Error al calcular el total de gastos","Detalle",e.getMessage()))
                );
    }

    public Mono<ServerResponse> listarGastos(ServerRequest request) {
        Flux<GastoResumenDTO> resumen = dataService.getGastos()
                .groupBy(gasto -> {
                    YearMonth yearMonth = YearMonth.from(gasto.getFecha());
                    return gasto.getEmpleadoId() + "-" + yearMonth;
                })
                .flatMap(group ->
                        group
                                .reduce(new GastoResumenDTO(),(dto,gasto)->{
                                    dto.setEmpleadoId(gasto.getEmpleadoId());
                                    dto.setMes(YearMonth.from(gasto.getFecha()));
                                    dto.setTotalGastos(dto.getTotalGastos()+gasto.getMonto());
                                    return dto;
                                })
                )
                .flatMap(gastos -> dataService.getEmpleado(gastos.getEmpleadoId())
                        .map(empleado -> {
                            gastos.setNombreEmpleado(empleado.getNombre());
                            return gastos;
                        })
                )
                .map(gastos -> {
                    gastos.setTotalGastos(gastos.getTotalGastos()*1.19);
                    gastos.setAsumeCompania(gastos.getTotalGastos()>1_000_000?"No":"Si");
                    return gastos;
                })
                .sort(Comparator.comparing(GastoResumenDTO::getNombreEmpleado));

        return resumen.collectList()
                        .flatMap(gastos -> {
                            if(gastos.isEmpty()){
                                return ServerResponse.noContent().build();
                            }
                            return ServerResponse.ok().bodyValue(gastos);
                        });
    }

    public Mono<ServerResponse> gastosEmpleado(ServerRequest request){
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
