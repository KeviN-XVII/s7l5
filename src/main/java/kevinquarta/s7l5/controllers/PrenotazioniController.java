package kevinquarta.s7l5.controllers;

import kevinquarta.s7l5.entities.Prenotazione;
import kevinquarta.s7l5.entities.Utente;
import kevinquarta.s7l5.exceptions.ValidationException;
import kevinquarta.s7l5.payloads.PrenotazioneDTO;
import kevinquarta.s7l5.services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {

    private final PrenotazioniService prenotazioniService;

    @Autowired
    public PrenotazioniController(PrenotazioniService prenotazioniService) {
        this.prenotazioniService = prenotazioniService;
    }

// ------POST CREA PRENOTAZIONE
    @PostMapping
    @PreAuthorize("hasAnyAuthority('UTENTE')")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione savePrenotazione(@AuthenticationPrincipal Utente currentUtente, @RequestBody @Validated PrenotazioneDTO payload, BindingResult validationResult){
        if(validationResult.hasErrors()){
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.prenotazioniService.savePrenotazione(payload, currentUtente);
        }
    }
}
