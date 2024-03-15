package com.app.mongodata.model;

import lombok.Data;

import java.util.List;

@Data
public class Bloque {
    private int id;
    private String title;
    private String desc;
    private List<Exercice> ejercicios;


}
