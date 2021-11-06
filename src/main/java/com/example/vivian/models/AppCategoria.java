package com.example.vivian.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_categoria")
public class AppCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;
    private String descripcion;
    @Column(columnDefinition = "bit default 1")
    private boolean esActivo;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaRegistro;
    @UpdateTimestamp
    private LocalDateTime fechaUpdate;
    
   /* @OneToMany(mappedBy = "oCategoria",fetch = FetchType.LAZY)
    private Set<AppProducto> productos;*/
}
