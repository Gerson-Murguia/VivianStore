package com.example.vivian.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_detalle_compra")
public class AppDetalleCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idDetalleCompra;
 
    @ManyToOne
    @JoinColumn(name = "id_compra")
	private AppCompra compra;
  
	@OneToOne
    @JoinColumn(name = "id_producto",nullable = false)
	private AppProducto oProducto;
	private int cantidad;
	private double total;
	
	@Transient
	private int idCompra;
    @Transient
	private int idProducto;
}
