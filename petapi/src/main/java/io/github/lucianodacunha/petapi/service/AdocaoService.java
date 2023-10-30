package io.github.lucianodacunha.petapi.service;

import io.github.lucianodacunha.petapi.dto.AprovacaoAdocaoDto;
import io.github.lucianodacunha.petapi.dto.ReprovacaoAdocaoDto;
import io.github.lucianodacunha.petapi.dto.SolicitacaoAdocaoDto;
import io.github.lucianodacunha.petapi.model.Adocao;
import io.github.lucianodacunha.petapi.model.Pet;
import io.github.lucianodacunha.petapi.model.StatusAdocao;
import io.github.lucianodacunha.petapi.model.Tutor;
import io.github.lucianodacunha.petapi.repository.AdocaoRepository;
import io.github.lucianodacunha.petapi.repository.PetRepository;
import io.github.lucianodacunha.petapi.repository.TutorRepository;
import io.github.lucianodacunha.petapi.validation.SolicitacaoAdocaoValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoService {
    @Autowired
    private AdocaoRepository adocaoRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private List<SolicitacaoAdocaoValidation> validacoes;

    public void solicitar(SolicitacaoAdocaoDto dto){
        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        validacoes.forEach(v -> v.validar(dto));

        Adocao adocao = new Adocao(tutor, pet, dto.motivo());
        adocaoRepository.save(adocao);

        emailService.sender(
                adocao.getPet().getAbrigo().getEmail(),
                "Solicitação de adoção",
                "Olá " +adocao.getPet().getAbrigo().getNome() +
                        "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +
                        adocao.getPet().getNome() +
                        ". \nFavor avaliar para aprovação ou reprovação."
        );
    }


    public void aprovar(AprovacaoAdocaoDto dto){
        Adocao adocao = adocaoRepository.getReferenceById(dto.idAdocao());

        adocao.marcarComoAprovado();
        adocaoRepository.save(adocao);

        emailService.sender(
                adocao.getPet().getAbrigo().getEmail(),
                "Adoção aprovada",
                "Parabéns " +adocao.getTutor().getNome() +
                        "!\n\nSua adoção do pet " +adocao.getPet().getNome() +
                        ", solicitada em " +
                        adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +
                        ", foi aprovada.\nFavor entrar em contato com o abrigo " +
                        adocao.getPet().getAbrigo().getNome() +
                        " para agendar a busca do seu pet."
        );
    }

    public void reprovar(ReprovacaoAdocaoDto dto){
        Adocao adocao = adocaoRepository.getReferenceById(dto.idAdocao());
        adocao.marcarComoReprovado(dto.justificativa());
        adocaoRepository.save(adocao);

        emailService.sender(
                adocao.getPet().getAbrigo().getEmail(),
                "Adoção reprovada",
                "Olá " +adocao.getTutor().getNome() +
                        "!\n\nInfelizmente sua adoção do pet " +
                        adocao.getPet().getNome() +", solicitada em " +
                        adocao.getData().format(DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy HH:mm:ss")) +", foi reprovada pelo abrigo " +
                        adocao.getPet().getAbrigo().getNome() +
                        " com a seguinte justificativa: " +
                        adocao.getJustificativaStatus()
        );
    }

}
