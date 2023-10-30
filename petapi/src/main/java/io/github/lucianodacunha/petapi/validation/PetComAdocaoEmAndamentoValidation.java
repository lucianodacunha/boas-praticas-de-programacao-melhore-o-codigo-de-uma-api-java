package io.github.lucianodacunha.petapi.validation;

import io.github.lucianodacunha.petapi.dto.SolicitacaoAdocaoDto;
import io.github.lucianodacunha.petapi.exception.ValidacaoException;
import io.github.lucianodacunha.petapi.model.Adocao;
import io.github.lucianodacunha.petapi.model.Pet;
import io.github.lucianodacunha.petapi.model.StatusAdocao;
import io.github.lucianodacunha.petapi.repository.AdocaoRepository;
import io.github.lucianodacunha.petapi.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PetComAdocaoEmAndamentoValidation implements SolicitacaoAdocaoValidation{

    @Autowired
    AdocaoRepository adocaoRepository;
    @Autowired
    PetRepository petRepository;

    public void validar(SolicitacaoAdocaoDto dto){
        Pet pet = petRepository.getReferenceById(dto.idPet());

        List<Adocao> adocoes = adocaoRepository.findAll();
        for (Adocao a : adocoes) {
            if (a.getPet() == pet && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
            }
        }
    }
}
