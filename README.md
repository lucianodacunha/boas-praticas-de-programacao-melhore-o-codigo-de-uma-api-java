# Boas práticas de programação: melhore o código de uma API Java

> "Qualquer tolo pode escrever um código que um computador possa entender. Bons programadores escrevem códigos que os humanos possam entender."


## Aula 1: Separação de responsabilidades

- Explorar o código fonte da aplicação para identificar possíveis melhorias;
- Realizar refatoração no código para extrair novas classes;
- Melhorar a separação de responsabilidades na aplicação.

## Aula 2: O padrão Data Transfer Object

- Utilizar o padrão DTO para representar os dados que chegam e saem na API;
- Mover validações de uma entidade JPA para uma classe DTO;
- Funciona o padrão DTO e suas vantagens.

### Retornando DTO no Controller

Utilizamos o padrão DTO para representar os dados que chegam na API, nas funcionalidades relacionadas com a adoção de pets. Na classe AdocaoController alteramos os métodos para receberem como parâmetro objetos DTO, ao invés de objetos que são entidades JPA.

Podemos utilizar também esse mesmo padrão nos métodos que devolvem informações da API, como por exemplo na requisição que lista os pets disponíveis para adoção, que está implementada na classe PetController:

```
@GetMapping
public ResponseEntity<List<Pet>> listarTodosDisponiveis() {
    List<Pet> pets = repository.findAll();
    List<Pet> disponiveis = new ArrayList<>();
    for (Pet pet : pets) {
        if (pet.getAdotado() == false) {
            disponiveis.add(pet);
        }
    }
    return ResponseEntity.ok(disponiveis);
}
```

Repare que esse método está devolvendo como resposta uma lista de objetos do tipo Pet, que é uma entidade JPA, sendo que o melhor seria retornar uma lista de objetos DTO.

Podemos então criar um record que representa os dados de um pet:

```
public record DadosDetalhesPet(Long id, TipoPet tipo, String nome, String raca, Integer idade) {

    public DadosDetalhesPet(Pet pet) {
        this(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade());
    }
}
```

E alterar o método no controller para devolver uma lista de objetos DTO:

```
@GetMapping
public ResponseEntity<List<DadosDetalhesPet>> listarTodosDisponiveis() {
    List<Pet> pets = repository.findAll();
    List<DadosDetalhesPet> disponiveis = new ArrayList<>();
    for (Pet pet : pets) {
        if (pet.getAdotado() == false) {
            disponiveis.add(new DadosDetalhesPet(pet));
        }
    }
    return ResponseEntity.ok(disponiveis);
}
```

Dessa forma seguimos a boa prática de utilizar o padrão DTO para representar os dados que chegam e também que saem da API, evitando utilizar as entidades JPA para isso.

## Aula 3: Validações flexíveis

- Aplicar o padrão de projeto Strategy para isolar as validações de regras de negócio;
- Aplicar o padrão de projeto Chain of Responsibility para utilizar todas as validações de maneira automática;
- Utilizar padrões de projetos para isolar validações pode deixar o código mais flexível.

### Outros padrões de projeto

Neste curso foram abordados apenas alguns padrões de projeto que são comuns em uma API Rest, como os Padrões GoF (Gang of Four) Strategy e Chain of Responsibility, assim como os padrões DTO (Data Transfer Object) e Service. No entanto, existem muitos outros padrões de projeto disponíveis que podem expandir ainda mais o seu conhecimento e ajudá-lo a projetar sistemas mais flexíveis e escaláveis.

Caso você tenha interesse em conhecer mais detalhes sobre os padrões utilizados no curso, bem como outros padrões que não foram abordados, você pode explorar alguns sites que explicam de maneira detalhada e com exemplos de códigos em Java. Os principais sites que indicamos são:

- [Refactoring Guru](https://refactoring.guru/pt-br/design-patterns): Um excelente site com explicações detalhadas e exemplos de padrões de projeto, além de também ensinar bastante sobre refatoração de códigos;
- [TutorialsPoint](https://tutorialspoint.com/design_pattern/index.htm): Oferece tutoriais abrangentes sobre padrões de projeto em Java;
- [Repositório no GitHub](https://github.com/iluwatar/java-design-patterns): Um repositório que contém uma ampla coleção de implementações de padrões de projeto em Java.

## Aula 4: Otimizações na camada de persistência

## Aula 5: Desafios

