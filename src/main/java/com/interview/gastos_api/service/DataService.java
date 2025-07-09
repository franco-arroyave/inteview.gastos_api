package com.interview.gastos_api.service;

import com.interview.gastos_api.model.Empleado;
import com.interview.gastos_api.model.Gasto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    private final List<Empleado> empleados = new ArrayList<>();
    private final List<Gasto> gastos = new ArrayList<>();

    public DataService(){
        empleados.add(new Empleado(1L, "Adam"));
        empleados.add(new Empleado(2L, "Bolton"));
        empleados.add(new Empleado(3L, "Chelsea"));
        empleados.add(new Empleado(4L, "Elsy"));
        empleados.add(new Empleado(5L, "Vincent"));
        empleados.add(new Empleado(6L, "Warden"));

        gastos.add(new Gasto(1L, 1L, LocalDate.of(2021, 1, 1), 2000000L));
        gastos.add(new Gasto(2L, 1L, LocalDate.of(2021, 1, 2), 1000000L));
        gastos.add(new Gasto(3L, 1L, LocalDate.of(2021, 3, 2), 500000L));
        gastos.add(new Gasto(4L, 2L, LocalDate.of(2021, 1, 1), 400000L));
        gastos.add(new Gasto(5L, 2L, LocalDate.of(2021, 3, 2), 1100000L));
        gastos.add(new Gasto(6L, 2L, LocalDate.of(2021, 1, 2), 500000L));
        gastos.add(new Gasto(7L, 3L, LocalDate.of(2021, 2, 1), 900000L));
        gastos.add(new Gasto(8L, 3L, LocalDate.of(2021, 2, 1), 59999L));
        gastos.add(new Gasto(9L, 3L, LocalDate.of(2021, 3, 2), 1100000L));
        gastos.add(new Gasto(10L, 4L, LocalDate.of(2021, 2, 1), 4000000L));
        gastos.add(new Gasto(11L, 5L, LocalDate.of(2021, 3, 2), 899999L));
        gastos.add(new Gasto(12L, 6L, LocalDate.of(2021, 2, 1), 5100000L));
        gastos.add(new Gasto(13L, 6L, LocalDate.of(2021, 3, 2), 1100000L));
    }

    public Mono<Empleado> getEmpleado(Long id){
        return Flux.fromIterable(empleados)
                .filter(empleado -> empleado.getId().equals(id))
                .next();
    }

    public Flux<Gasto> getGastos() {
        return Flux.fromIterable(gastos);
    }

    public Flux<Gasto> getGastosPorEmpleado(Long empleadoId) {
        return Flux.fromIterable(gastos)
                .filter(g -> g.getEmpleadoId().equals(empleadoId) );
    }

}
