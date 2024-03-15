package com.app.mongodata.service;

import com.app.mongodata.model.Wod;

import java.util.List;
import java.util.Optional;

public interface IWodService {

    List<Wod> buscar();
    Wod burcarPorId(String id);
    Wod guardar(Wod wod);
    void eliminar (String id);



}
