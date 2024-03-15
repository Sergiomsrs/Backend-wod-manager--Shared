package com.app.mongodata.dao;

import com.app.mongodata.model.Wod;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IWodDao extends MongoRepository<Wod, String> {

    Optional<Wod> findTopByOrderByIdDesc();
}
