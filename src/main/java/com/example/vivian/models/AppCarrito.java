package com.example.vivian.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_carrito")
public class AppCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;
    private AppProducto oProducto;
    private AppUsuario oUsuario;
    /*FK
    * private int idUsuario;
    * private int idProducto;
    * */
}
