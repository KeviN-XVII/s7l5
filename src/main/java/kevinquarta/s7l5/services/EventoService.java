package kevinquarta.s7l5.services;

import kevinquarta.s7l5.repositories.EventiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventoService {

    private final EventiRepository eventiRepository;

    @Autowired
    public EventoService(EventiRepository eventiRepository) {
        this.eventiRepository = eventiRepository;
    }

    




}
