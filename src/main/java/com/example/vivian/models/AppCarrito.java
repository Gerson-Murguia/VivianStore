package com.example.vivian.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_carrito")
public class AppCarrito {
	//La clase funciona como un item de carrito que se asocia a un usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrito;
    //el itemcarrito solo puede tener un producto y viceversa
    @OneToOne
    @JoinColumn(name = "id_producto",nullable = false,unique = true)
    private AppProducto producto;
    
    //El usuario puede tener muchos carritos
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario",nullable = false)
    @JsonIgnore
    private AppUsuario usuario;
}
