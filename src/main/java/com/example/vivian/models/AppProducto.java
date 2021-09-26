package com.example.vivian.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_producto")
public class AppProducto {

    @Id
    private int idProducto;
    private String nombre;
    private String descripcion;
    @Column(columnDefinition = "decimal(10,2) default 0")
    private double precio;
    private int  stock;
    private String rutaImagen;
    private boolean esActivo;
    @CreationTimestamp
    private LocalDate fechaRegistro;
    /*FK
    private int idMarca;
    private int idCategoria;
    * */
    //ManyToOne

}
