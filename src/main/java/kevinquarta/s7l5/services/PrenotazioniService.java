package kevinquarta.s7l5.services;

import kevinquarta.s7l5.entities.Evento;
import kevinquarta.s7l5.entities.Prenotazione;
import kevinquarta.s7l5.entities.Utente;
import kevinquarta.s7l5.exceptions.NotFoundException;
import kevinquarta.s7l5.payloads.PrenotazioneDTO;
import kevinquarta.s7l5.repositories.EventiRepository;
import kevinquarta.s7l5.repositories.PrenotazioniRepository;
import kevinquarta.s7l5.repositories.UtentiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrenotazioniService {

    private final UtentiRepository utentiRepository;
    private final EventiRepository eventiRepository;
    private final PrenotazioniRepository prenotazioniRepository;

    @Autowired
    public PrenotazioniService(UtentiRepository utentiRepository, EventiRepository eventiRepository, PrenotazioniRepository prenotazioniRepository) {
        this.utentiRepository = utentiRepository;
        this.eventiRepository = eventiRepository;
        this.prenotazioniRepository = prenotazioniRepository;
    }

// ------SALVA PRENOTAZIONE
     public Prenotazione savePrenotazione (PrenotazioneDTO payload) {
// ------RICERCO UTENTE E VIAGGIO
          Utente utente = utentiRepository.findById(payload.utenteid())
            .orElseThrow(()->new NotFoundException(payload.utenteid()));
          Evento evento = eventiRepository.findById(payload.eventoId())
            .orElseThrow(()->new NotFoundException(payload.eventoId()));

// -------NUOVA PRENOTAZIONE
          Prenotazione newPrenotazione = new Prenotazione(
                utente,evento
         );
//       SALVA PRENOTAZIONE
          Prenotazione savedPrenotazione = prenotazioniRepository.save(newPrenotazione);
//        LOG
         log.info("La prenotazione per l'evento " + newPrenotazione.getEvento().getTitolo() +" in data "+ newPrenotazione.getEvento().getDataEvento() + " è stata salvata correttamente!");
           return savedPrenotazione;
     }



}
