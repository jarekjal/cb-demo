package com.jarekjal.cbdemo.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Scope;

import java.time.ZonedDateTime;
import java.util.List;

@Document
@Scope("dev")
@Collection("person")
@Value
@Builder(toBuilder = true)
public class Person {

    @Id
    String id;
    String pid;
    String name;
    String surname;
    String mobile;
    List<String> aliases;
    Address address;
    ZonedDateTime effectiveFrom;
    ZonedDateTime effectiveTo;
    Boolean active;
    //Metadata:
    Boolean lastVersion;
    Long versionNumber;

//    @Version
//    Long version; // for optimistic locking
}
