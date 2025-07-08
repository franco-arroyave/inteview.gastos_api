package com.interview.gastos_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Empleado {
    private Long id;
    private String nombre;
}
