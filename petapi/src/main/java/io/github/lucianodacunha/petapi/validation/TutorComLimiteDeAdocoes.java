package io.github.lucianodacunha.petapi.validation;

import io.github.lucianodacunha.petapi.dto.SolicitacaoAdocaoDto;
import io.github.lucianodacunha.petapi.exception.ValidacaoException;
import io.github.lucianodacunha.petapi.model.Adocao;
import io.github.lucianodacunha.petapi.model.StatusAdocao;
import io.github.lucianodacunha.petapi.model.Tutor;
import io.github.lucianodacunha.petapi.repository.AdocaoRepository;
import io.github.lucianodacunha.petapi.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TutorComLimiteDeAdocoes implements SolicitacaoAdocaoValidation{

    @Autowired
    private AdocaoRepository adocaoRepository;
    @Autowired
    private TutorRepository tutorRepository;

    public void validar(SolicitacaoAdocaoDto dto){
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        List<Adocao> adocoes = adocaoRepository.findAll();
        for (Adocao a : adocoes) {
            int contador = 0;
            if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.APROVADO) {
                contador = contador + 1;
            }
            if (contador == 5) {
                throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
            }
        }
    }
}
