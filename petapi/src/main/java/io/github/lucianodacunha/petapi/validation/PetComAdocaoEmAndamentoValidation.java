package io.github.lucianodacunha.petapi.validation;

import io.github.lucianodacunha.petapi.dto.SolicitacaoAdocaoDto;
import io.github.lucianodacunha.petapi.exception.ValidacaoException;
import io.github.lucianodacunha.petapi.model.StatusAdocao;
import io.github.lucianodacunha.petapi.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetComAdocaoEmAndamentoValidation implements SolicitacaoAdocaoValidation{

    @Autowired
    AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDto dto) {
        boolean petComAdocaoEmAndamento = adocaoRepository.existsByPetIdAndStatus(
                dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO
        );

        if (petComAdocaoEmAndamento) {
            throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
        }
    }
}
