package com.example.vivian.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_producto")
public class AppProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProducto;
    private String nombre;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name="id_categoria",nullable = false,foreignKey = @ForeignKey(name="FK_CATEGORIA_PRODUCTO"))
    private AppCategoria oCategoria;
    @Column(columnDefinition = "decimal(10,2) default 0")
    private double precio;
    private int  stock;
    private String rutaImagen;
    private boolean esActivo;
    private String base64;
    private String extension;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaRegistro;
    @UpdateTimestamp
    private LocalDateTime fechaUpdate;
    
    /*FK
    private int idMarca;

    * */
    //ManyToOne

}
