package io.github.lucianodacunha.petapi.controller;


import io.github.lucianodacunha.petapi.exception.ValidacaoException;
import io.github.lucianodacunha.petapi.model.Adocao;
import io.github.lucianodacunha.petapi.model.StatusAdocao;
import io.github.lucianodacunha.petapi.repository.AdocaoRepository;
import io.github.lucianodacunha.petapi.service.AdocaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/adocoes")
//@ComponentScan("org.springframework.mail.javamail.JavaMailSender")
public class AdocaoController {

    @Autowired
    private AdocaoService adocaoService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> solicitar(@RequestBody @Valid Adocao adocao) {
        try {
            adocaoService.solicitar(adocao);
            return ResponseEntity.ok().body("Adoção solicitada com sucesso!");
        } catch (ValidacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/aprovar")
    @Transactional
    public ResponseEntity<String> aprovar(@RequestBody @Valid Adocao adocao) {
        adocaoService.aprovar(adocao);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reprovar")
    @Transactional
    public ResponseEntity<String> reprovar(@RequestBody @Valid Adocao adocao) {

        adocaoService.reprovar(adocao);

        return ResponseEntity.ok().build();
    }

}
