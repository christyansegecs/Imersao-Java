package com.chris.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LinguagemController {

    @Autowired
    private LinguagemRepository repository;

    private List<Linguagem> linguagens = 
    List.of(
        new Linguagem("Java", "https://raw.githubusercontent.com/abrahamcalf/programming-languages-logos/master/src/java/java_512x512.png", 1),
        new Linguagem("Kotlin", "https://raw.githubusercontent.com/abrahamcalf/programming-languages-logos/master/src/kotlin/kotlin_512x512.png", 2)
    );

    @GetMapping("/api")
    public String processaLinguagemPreferida() {
        return "Hello World";
    }

    @GetMapping("/linguagens")
    public List<Linguagem> obterLinguagens() {
        return linguagens;
    }

    @GetMapping("/linguagens-db")
    public List<Linguagem> mongoDBLinguagens() {
        List<Linguagem> linguagens = repository.findByOrderByRanking();
        return linguagens;
    }

    @PostMapping("/linguagens-db")
    public ResponseEntity<Linguagem> cadastrarLinguagem(@RequestBody Linguagem linguagem) {
        Linguagem linguagemSalva = repository.save(linguagem);
        return new ResponseEntity<>(linguagemSalva, HttpStatus.CREATED) ;
    }

    @GetMapping("/linguagens-db/{id}")
    public Linguagem obterLinguagemPorId(@PathVariable String id) {
        return repository.findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping("/linguagens-db/{id}")
    public Linguagem atualizarLinguagem(@PathVariable String id, @RequestBody Linguagem linguagem) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
       linguagem.setId(id); 
        Linguagem linguagemSalva = repository.save((linguagem));
        return linguagemSalva;
    }

    @DeleteMapping("/linguagens-db/{id}")
    public void excluirLinguagem(@PathVariable String id) {
        repository.deleteById(id);
    }
}
