package kevinquarta.s7l5.services;

import kevinquarta.s7l5.entities.Utente;
import kevinquarta.s7l5.exceptions.BadRequestException;
import kevinquarta.s7l5.exceptions.NotFoundException;
import kevinquarta.s7l5.payloads.UtenteDTO;
import kevinquarta.s7l5.repositories.UtentiRepository;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UtentiService {

    private final UtentiRepository utentiRepository;
    private final PasswordEncoder bcrypt;

    @Autowired
    public UtentiService(UtentiRepository utentiRepository, PasswordEncoder bcrypt) {
        this.utentiRepository = utentiRepository;
        this.bcrypt = bcrypt;
    }


// -----SAVE UTENTE
    public Utente saveUtente(UtenteDTO payload){
// -----CONTROLLO EMAIL
        this.utentiRepository.findByEmail(payload.email()).ifPresent(utente -> {
            throw new BadRequestException("L'email "+ utente.getEmail() + " è già registrata!");});
// -----NUOVO UTENTE
        Utente newUtente = new Utente(payload.nome(),payload.cognome(),payload.email(),bcrypt.encode(payload.password()));
// -----SALVO UTENTE
        Utente savedUtente = this.utentiRepository.save(newUtente);
// -----LOG
        log.info("Utente"+newUtente.getNome()+" "+newUtente.getCognome() +" salvato con successo: ");
        return savedUtente;
    }


// -----FIND BY ID
    public Utente findById(Long utenteId){
        return this.utentiRepository.findById(utenteId)
            .orElseThrow(()-> new NotFoundException(utenteId));
    }

// -----FIND ALL
    public Page<Utente> findAll(int page, int size, String orderBy, String sortCriteria) {
       if (size > 100 || size < 0) size = 10;
       if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
            sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
    return this.utentiRepository.findAll(pageable);
}







}
