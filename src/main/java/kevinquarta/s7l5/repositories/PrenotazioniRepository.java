package kevinquarta.s7l5.repositories;


import kevinquarta.s7l5.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, Long> {
}
