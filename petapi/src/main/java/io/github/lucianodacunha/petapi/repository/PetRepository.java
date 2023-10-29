package io.github.lucianodacunha.petapi.repository;

import io.github.lucianodacunha.petapi.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {}
