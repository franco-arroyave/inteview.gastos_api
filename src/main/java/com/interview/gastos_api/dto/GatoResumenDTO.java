package com.interview.gastos_api.dto;

import lombok.Data;

import java.time.YearMonth;

@Data
public class GatoResumenDTO {
    private Long empleadoId;
    private String nombreEmpleado;
    private YearMonth mes;
    private Long totalGastos;
    private boolean asumeCompania;
}
