package kevinquarta.s7l5.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EventoDTO(
        @NotBlank(message = "Il titolo è obbligatorio")
        @Size(min=2,max=30)
        String titolo,
        @NotBlank(message = "La descrizione è obbligatoria")
        @Size(min=2,max=30)
        String descrizione,
        @NotNull(message = "La data dell'evento è obbligatoria")
        @Future(message = "L'evento deve essere in futuro")
        LocalDate dataEvento,
        @NotBlank(message = "Il luogo è obbligatorio")
        @Size(min=2,max=30)
        String luogo,
        @NotNull(message = "Il numero massimo partecipanti è obbligatoria")
        int numPosti
) {
}
