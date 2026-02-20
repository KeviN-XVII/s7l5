package kevinquarta.s7l5.controllers;


import kevinquarta.s7l5.entities.Evento;
import kevinquarta.s7l5.entities.Utente;
import kevinquarta.s7l5.exceptions.ValidationException;
import kevinquarta.s7l5.payloads.EventoDTO;
import kevinquarta.s7l5.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")
public class EventiController {

    private final EventoService eventoService;

    @Autowired
    public EventiController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

//  ---POST CREA EVENTO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento saveEvento(@AuthenticationPrincipal Utente currentOrganizzatore,
                             @RequestBody @Validated EventoDTO payload,
                             BindingResult validationResult){
        if(validationResult.hasErrors()){
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.eventoService.saveEvento(payload, currentOrganizzatore);
        }
    }

//  ---GET LISTA EVENTI
     @GetMapping
     @PreAuthorize("hasAuthority('ORGANIZZATORE')")
     public Page<Evento> findAll(@RequestParam(defaultValue = "0")int page,
                                  @RequestParam(defaultValue = "10")int size,
                                  @RequestParam(defaultValue = "titolo")String orderBy,
                                  @RequestParam(defaultValue = "asc")String sortCriteria) {
     return eventoService.findAll(page, size, orderBy, sortCriteria);
     }

//  ----PUT MODIFICA EVENTO
    @PutMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Evento findEventoIdAndUpdate(@PathVariable long eventoId,
                                        @AuthenticationPrincipal Utente currentOrganizzatore,
                                        @RequestBody @Validated EventoDTO payload,
                                        BindingResult validationResult){
        if(validationResult.hasErrors()){
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        } else {
            return this.eventoService.findByIdAndUpdateEvento(eventoId,payload, currentOrganizzatore);
        }
    }

//  ----DELETE ELIMINA EVENTO
      @DeleteMapping("/{eventoId}")
      @PreAuthorize("hasAuthority('ORGANIZZATORE')")
      @ResponseStatus(HttpStatus.NO_CONTENT)
      public void deleteViaggio(@PathVariable long eventoId,@AuthenticationPrincipal Utente currentOrganizzatore) {
           this.eventoService.findByIdAndDelete(eventoId,currentOrganizzatore);
      }

}

