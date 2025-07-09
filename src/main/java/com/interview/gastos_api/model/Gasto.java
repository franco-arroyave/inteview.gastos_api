package com.interview.gastos_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Gasto {
    private Long id;
    private Long empleadoId;
    private LocalDate fecha;
    private double monto;
}
