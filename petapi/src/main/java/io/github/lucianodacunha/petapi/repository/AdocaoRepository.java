package io.github.lucianodacunha.petapi.repository;

import io.github.lucianodacunha.petapi.model.Adocao;
import io.github.lucianodacunha.petapi.model.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    boolean existsByPetIdAndStatus(Long idPet, StatusAdocao statusAdocao);

    boolean existsByTutorIdAndStatus(Long idTutor, StatusAdocao statusAdocao);

    Long countByTutorIdAndStatus (Long idTutor, StatusAdocao statusAdocao);
}
