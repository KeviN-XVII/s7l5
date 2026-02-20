package kevinquarta.s7l5.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="prenotazioni")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @ManyToOne
    @JoinColumn(name="utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name="evento_id")
    private Evento evento;

    private LocalDate dataPrenotazione;

    public Prenotazione(Utente utente, Evento evento) {
        this.utente = utente;
        this.evento = evento;
        this.dataPrenotazione = LocalDate.now();
    }
}
