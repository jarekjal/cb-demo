package com.jarekjal.cbdemo.service;

import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.transactions.TransactionQueryOptions;
import com.couchbase.transactions.Transactions;
import com.jarekjal.cbdemo.model.Person;
import com.jarekjal.cbdemo.repository.PersonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.core.mapping.CouchbaseDocument;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonCreationService {

    final PersonRepo personRepo;
    final Transactions transactions;
    final CouchbaseClientFactory couchbaseClientFactory;
    final MappingCouchbaseConverter mappingCouchbaseConverter;

    public void createPerson(Person person) {
        String pid = person.getPid();
        if (!personRepo.existsByPid(pid)) {
            personRepo.save(person);
        } else {
            createPersonWhenPreviousVersionsExists(pid, person);
        }
    }

    private void createPersonWhenPreviousVersionsExists(String pid, Person newPerson) {
        try {
            transactions.run(ctx -> {
                Scope scope = couchbaseClientFactory.withScope("dev").getScope();
                Collection collection = couchbaseClientFactory.withScope("dev").getCollection("person");
                // Performing a SELECT N1QL query against a scope:
                QueryResult qr = ctx.query(scope, "SELECT meta().id, * FROM person WHERE pid = $1 and lastVersion = true",
                        TransactionQueryOptions.queryOptions()
                                .parameters(JsonArray.from(pid)));
                List<JsonObject> rows = qr.rowsAs(JsonObject.class);
                Long previousVersionNumber = ((JsonObject) rows.get(0).get("person")).getLong("versionNumber");
                Long newVersionNumber = previousVersionNumber + 1;
                String previousId = rows.get(0).getString("id");
                Person toSave = newPerson.toBuilder()
                        .id(previousId + newVersionNumber)
                        .lastVersion(true)
                        .versionNumber(newVersionNumber)
                        .build();
                CouchbaseDocument target = new CouchbaseDocument();
                mappingCouchbaseConverter.write(toSave, target);
                ctx.insert(collection, target.getId(), target.export());
                // Performing an UPDATE N1QL query on multiple documents, in the scope:
                ctx.query(scope, "UPDATE person SET lastVersion = false WHERE meta().id = $1",
                        TransactionQueryOptions.queryOptions()
                                .parameters(JsonArray.from(previousId)));
            });
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong with transaction!" + e);
        }
    }
}
