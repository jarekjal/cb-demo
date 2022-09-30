package com.jarekjal.cbdemo.controller;

import com.couchbase.transactions.TransactionGetResult;
import com.couchbase.transactions.Transactions;
import com.jarekjal.cbdemo.model.Person;
import com.jarekjal.cbdemo.repository.PersonRepo;
import com.jarekjal.cbdemo.service.PersonCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.core.mapping.CouchbaseDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonController {

    final PersonRepo personRepo;

    final PersonCreationService personCreationService;
    final CouchbaseClientFactory couchbaseClientFactory;
    final Transactions transactions;
    final MappingCouchbaseConverter mappingCouchbaseConverter;


    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable("id") String id, @RequestBody Person person) {

        transactions.run(ctx -> {
            var collection = couchbaseClientFactory.withScope("dev").getCollection("person");
            TransactionGetResult getResult = ctx.get(collection, id);
            var content = getResult.contentAsObject();
            Person personNew = Person.builder().id("dddd").surname("xxx").build();
            CouchbaseDocument target = new CouchbaseDocument();
            mappingCouchbaseConverter.write(personNew, target);
            ctx.insert(collection, target.getId(), target.export());
//            ctx.rollback();
        });


        return person;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String savePerson(@RequestBody Person person) {
        personCreationService.createPerson(person);
        return "Person saved successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Person> getPersons(Pageable pageable) {
        return personRepo.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable("id") String id) {
        return personRepo.findById(id).orElse(null);
    }

    @GetMapping("/alias")
    public List<Person> getPersonsByAlias(@RequestParam("aliases") List<String> aliases, Pageable pageable) {
        return personRepo.findIfAliasesContainsAnyOfGiven(aliases, pageable);
    }

}
