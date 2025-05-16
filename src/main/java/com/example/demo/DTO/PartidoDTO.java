package com.example.demo.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PartidoDTO {
    private Long id;

    @NotNull(message = "El ID del torneo es obligatorio")
    private Long torneoId;

    @NotNull(message = "El ID del equipo 1 es obligatorio")
    private Long equipo1Id;

    @NotNull(message = "El ID del equipo 2 es obligatorio")
    private Long equipo2Id;

    @NotNull(message = "La fecha y hora del partido es obligatoria")
    private LocalDateTime fechaHora;

    @PositiveOrZero(message = "Los puntos del equipo 1 deben ser 0 o más")
    private Integer puntosEquipo1;

    @PositiveOrZero(message = "Los puntos del equipo 2 deben ser 0 o más")
    private Integer puntosEquipo2;

    // ID del equipo ganador, puede ser null en caso de empate o partido no definido aún
    private Long ganadorId;
}
