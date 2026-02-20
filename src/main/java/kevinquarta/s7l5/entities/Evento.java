package kevinquarta.s7l5.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="eventi")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    private String titolo;
    private String descrizione;
    private LocalDate dataEvento;
    private String luogo;
    private int numPosti;

    @ManyToOne
    @JoinColumn(name="organizzatore_id")
    private Utente organizzatore;

    public Evento(String titolo, String descrizione, LocalDate dataEvento, String luogo, int numPosti, Utente organizzatore) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.dataEvento = dataEvento;
        this.luogo = luogo;
        this.numPosti = numPosti;
        this.organizzatore = organizzatore;
    }




}
