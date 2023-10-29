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

## Aula 4: Otimizações na camada de persistência

## Aula 5: Desafios

