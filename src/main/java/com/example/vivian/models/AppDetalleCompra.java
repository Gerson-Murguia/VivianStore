package com.example.vivian.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_detalle_compra")
public class AppDetalleCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDetalleCompra;
	private int idCompra;
	private int idProducto;
	private AppProducto oProducto;
	private int cantidad;
	private double total;

}
