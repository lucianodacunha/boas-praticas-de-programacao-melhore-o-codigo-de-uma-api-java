package io.github.lucianodacunha.petapi.validation;

import io.github.lucianodacunha.petapi.dto.SolicitacaoAdocaoDto;
import io.github.lucianodacunha.petapi.exception.ValidacaoException;
import io.github.lucianodacunha.petapi.model.StatusAdocao;
import io.github.lucianodacunha.petapi.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TutorComAdocaoEmAndamentoValidation implements SolicitacaoAdocaoValidation{
    @Autowired
    private AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDto dto){
        boolean tutorComAdocaoEmAndamento = adocaoRepository.existsByTutorIdAndStatus(
                dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO
        );

        if (tutorComAdocaoEmAndamento) {
            throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
        }
    }
}
