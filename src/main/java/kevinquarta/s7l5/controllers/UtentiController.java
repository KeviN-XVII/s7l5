package kevinquarta.s7l5.controllers;


import kevinquarta.s7l5.entities.Utente;
import kevinquarta.s7l5.services.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utenti")
public class UtentiController {

    public UtentiService  utentiService;

    @Autowired
    public UtentiController(UtentiService utentiService) {
        this.utentiService = utentiService;
    }


    @GetMapping
    public Page<Utente> findAll(@RequestParam(defaultValue = "0")int page,
                                @RequestParam(defaultValue = "10")int size,
                                @RequestParam(defaultValue = "nome")String orderBy,
                                @RequestParam(defaultValue = "asc")String sortCriteria) {
        return utentiService.findAll(page, size, orderBy, sortCriteria);
    }



}
