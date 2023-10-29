package io.github.lucianodacunha.petapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "tipo")
    private TipoPet tipo;

    @NotBlank
    @Column(name = "nome")
    private String nome;

    @NotBlank
    @Column(name = "raca")
    private String raca;

    @NotNull
    @Column(name = "idade")
    private Integer idade;

    @NotBlank
    @Column(name = "cor")
    private String cor;

    @NotNull
    @Column(name = "peso")
    private Float peso;

    @Column(name = "adotado")
    private Boolean adotado;

    @ManyToOne
    @JsonBackReference("abrigo_pets")
    @JoinColumn(name = "abrigo_id")
    private Abrigo abrigo;

    @OneToOne(mappedBy = "pet")
    @JsonBackReference("adocao_pets")
    private Adocao adocao;
}