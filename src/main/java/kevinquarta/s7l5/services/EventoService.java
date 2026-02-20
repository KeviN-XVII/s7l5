package kevinquarta.s7l5.services;

import kevinquarta.s7l5.entities.Evento;
import kevinquarta.s7l5.entities.Utente;
import kevinquarta.s7l5.exceptions.NotFoundException;
import kevinquarta.s7l5.exceptions.UnauthroizedException;
import kevinquarta.s7l5.payloads.EventoDTO;
import kevinquarta.s7l5.repositories.EventiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventoService {

    private final EventiRepository eventiRepository;

    @Autowired
    public EventoService(EventiRepository eventiRepository) {
        this.eventiRepository = eventiRepository;
    }

// ------SAVE EVENTO
    public Evento saveEvento(EventoDTO payload, Utente organizzatore){
// ------NUOVO EVENTO
      Evento newEvento = new Evento(payload.titolo(), payload.descrizione(),payload.dataEvento(), payload.luogo(), payload.numPosti(),organizzatore);
// ------SALVO EVENTO
      Evento savedEvento = eventiRepository.save(newEvento);
//       LOG
       log.info("L'Evento " + savedEvento.getTitolo() +" è stato salvato correttamente");
      return savedEvento;
    }


// -------FIND BY ID
    public Evento findById(Long eventoId){
        return this.eventiRepository.findById(eventoId)
                .orElseThrow(()-> new NotFoundException(eventoId));
    }



// ------MODIFICA EVENTO
      public Evento findByIdAndUpdateEvento (Long eventoId,EventoDTO payload,Utente organizzatore) {

// -------RICERCA EVENTO
          Evento found = this.findById(eventoId);
// -------CONTROLLO ORGANIZZATORE
          if (found.getOrganizzatore().getId()==organizzatore.getId()){
// -------MODIFICA EVENTO
              found.setTitolo(payload.titolo());
              found.setDescrizione(payload.descrizione());
              found.setDataEvento(payload.dataEvento());
              found.setLuogo(payload.luogo());
              found.setNumPosti(payload.numPosti());

// -------SAVE EVENTO
              Evento modifiedEvento = eventiRepository.save(found);

// -------LOG
              log.info("L'evento è stato modificato correttamente");
              return modifiedEvento;
          } else {

              throw new UnauthroizedException("Solo il creatore di questo evento può modificarlo");
          }
      }


// ------LISTA EVENTI
      public Page<Evento> findAll(int page, int size, String orderBy, String sortCriteria) {
           if (size > 100 || size < 0) size = 10;
           if (page < 0) page = 0;
           Pageable pageable = PageRequest.of(page, size,
                 sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
           return this.eventiRepository.findAll(pageable);
      }


// ------ELIMINA EVENTO

public void findByIdAndDelete(Long eventoId,Utente organizzatore){
    Evento found = this.findById(eventoId);
    // -------CONTROLLO ORGANIZZATORE
    if (found.getOrganizzatore().getId()==organizzatore.getId()){
        this.eventiRepository.delete(found);
    } else {
        throw new UnauthroizedException("Solo il creatore di questo evento può eliminarlo");
    }

}




}
