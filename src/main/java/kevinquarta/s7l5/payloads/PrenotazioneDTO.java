package kevinquarta.s7l5.payloads;

import jakarta.validation.constraints.NotNull;

public record PrenotazioneDTO(
        @NotNull(message="L'id evento è obbligatorio")
        Long eventoId
        ) {
}
