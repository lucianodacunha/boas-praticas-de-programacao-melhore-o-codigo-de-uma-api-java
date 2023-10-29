package io.github.lucianodacunha.petapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adocoes")
public class Adocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private LocalDateTime data;

    @ManyToOne
    @JsonBackReference("tutor_adocoes")
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @OneToOne
    @JoinColumn(name = "pet_id")
    @JsonManagedReference("adocao_pets")
    private Pet pet;

    @Column(name = "motivo")
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusAdocao status;

    @Column(name = "justificativa_status")
    private String justificativaStatus;
}