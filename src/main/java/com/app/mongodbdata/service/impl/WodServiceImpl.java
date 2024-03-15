package com.app.mongodata.service.impl;

import com.app.mongodata.dao.IWodDao;
import com.app.mongodata.model.Wod;
import com.app.mongodata.service.IWodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WodServiceImpl implements IWodService {
    @Autowired
    private IWodDao wodDao;

    @Override
    public List<Wod> buscar() {
        return wodDao.findAll();
    }

    @Override
    public Wod burcarPorId(String id) {
        Optional<Wod> wod = wodDao.findById(id);
        return wod.orElse(null);
    }

    @Override
    public Wod guardar(Wod wod) {
        return wodDao.save(wod);
    }

    @Override
    public void eliminar(String id) {
    wodDao.deleteById(id);
    }


}
