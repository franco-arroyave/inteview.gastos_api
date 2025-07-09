package com.interview.gastos_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Data
@NoArgsConstructor
public class GastoResumenDTO {
    private Long empleadoId;
    private String nombreEmpleado;
    private YearMonth mes;
    private double totalGastos = 0;
    private String asumeCompania;
}
