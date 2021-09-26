package com.example.vivian.models;

import java.time.LocalDate;
import java.util.List;

public class AppPedido {
    private int id;
    private int idcliente;
    private int idpago;
    private LocalDate fecha;
    private double monto;
    private String estado;
    private List<AppDetallePedido>detallecompras;
}
