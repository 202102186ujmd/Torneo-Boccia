package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipoDTO {
    private Long id;

    @NotBlank(message = "El nombre del equipo no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    @NotBlank(message = "El país no puede estar vacío")
    @Size(max = 100, message = "El país no puede tener más de 100 caracteres")
    private String pais;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;  // Faltaba categoría, ya que en el modelo está asociado
}
