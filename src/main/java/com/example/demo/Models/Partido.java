package com.example.demo.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "partido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "torneo_id")
    private Torneo torneo;

    @ManyToOne
    @JoinColumn(name = "equipo1_id")
    private Equipo equipo1;

    @ManyToOne
    @JoinColumn(name = "equipo2_id")
    private Equipo equipo2;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "puntos_equipo1")
    private Integer puntosEquipo1;

    @Column(name = "puntos_equipo2")
    private Integer puntosEquipo2;

    @ManyToOne
    @JoinColumn(name = "ganador_id")
    private Equipo ganador;
}
