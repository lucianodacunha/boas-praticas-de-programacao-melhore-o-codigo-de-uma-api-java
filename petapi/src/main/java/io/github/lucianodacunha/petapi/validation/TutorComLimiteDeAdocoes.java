package io.github.lucianodacunha.petapi.validation;

import io.github.lucianodacunha.petapi.dto.SolicitacaoAdocaoDto;
import io.github.lucianodacunha.petapi.exception.ValidacaoException;
import io.github.lucianodacunha.petapi.model.StatusAdocao;
import io.github.lucianodacunha.petapi.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TutorComLimiteDeAdocoes implements SolicitacaoAdocaoValidation{

    @Autowired
    private AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDto dto) {
        Long quantidadeDeAdocaoPorTutor = adocaoRepository.countByTutorIdAndStatus(
                dto.idTutor(), StatusAdocao.APROVADO
        );

        if (quantidadeDeAdocaoPorTutor == 5) {
            throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
        }
    }
}
