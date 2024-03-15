package com.app.mongodata.model;



import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document(collection = "wod")
public class Wod {
    @Id
    private String id;
    private List<Bloque> bloques;
}
