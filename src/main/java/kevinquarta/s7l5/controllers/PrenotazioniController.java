package kevinquarta.s7l5.controllers;

import kevinquarta.s7l5.entities.Prenotazione;
import kevinquarta.s7l5.exceptions.ValidationException;
import kevinquarta.s7l5.payloads.PrenotazioneDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public class PrenotazioniController {




    @PostMapping
    @PreAuthorize("hasAnyAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazioneDTO payload, BindingResult validationResult){
        if(validationResult.hasErrors()){
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.eventoService;
        }
    }
}
