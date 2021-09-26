package com.example.vivian.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_marca")
public class AppMarca {


    @Id
    private Long idMarca;
    private String descripcion;
    @Column(columnDefinition = "bit default 1")
    private boolean esActivo;
    @CreationTimestamp
    private LocalDate fechaRegistro;
}
