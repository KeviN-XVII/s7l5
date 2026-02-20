package kevinquarta.s7l5.repositories;

import kevinquarta.s7l5.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventiRepository extends JpaRepository<Evento, Long> {
}
