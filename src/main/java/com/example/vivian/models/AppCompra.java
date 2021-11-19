package com.example.vivian.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;


@Data
@Entity
@Table(name = "tb_compra")
public class AppCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCompra;
    private int idUsuario;
    private String totalProducto;
    private double total;
    public String contacto;
    public String telefono;
    public String direccion;
    public String idDistrito;
    public String fecha;
    @OneToMany(mappedBy = "compra")
    public List<AppDetalleCompra> oDetalleCompra;
}
