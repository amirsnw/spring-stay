package com.stay.resource;

import com.stay.domain.mongoDocument.PassengerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends MongoRepository<PassengerDocument, Integer> {
    @Query("{firstName:'?0'}")
    PassengerDocument findItemByFirstName(String firstName);

    @Query(value="{nationality:'?0'}", fields="{'firstName' : 1, 'lastName' : 1}")
    List<PassengerDocument> findAll(String nationality);

    public long count();
}
