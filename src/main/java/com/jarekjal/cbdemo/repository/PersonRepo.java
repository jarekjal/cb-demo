package com.jarekjal.cbdemo.repository;

import com.jarekjal.cbdemo.model.Person;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepo extends CouchbaseRepository<Person, String> {

    //@Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} and surname = $1")
    List<Person> findBySurname(String surname, Pageable pageable);

    //https://stackoverflow.com/questions/28861860/in-couchbase-or-n1ql-how-can-i-check-if-the-values-in-an-array-match
    @Query("SELECT #{#n1ql.fields} FROM #{#n1ql.bucket} WHERE #{#n1ql.filter} and lastVersion = true and ANY a IN aliases SATISFIES a IN $1 END")
    List<Person> findIfAliasesContainsAnyOfGiven(List<String> alias, Pageable pageable);

    boolean existsByPid(String pid);

}
