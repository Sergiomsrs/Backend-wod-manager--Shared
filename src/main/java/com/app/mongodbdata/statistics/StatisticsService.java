package com.app.mongodata.statistics;

import com.app.mongodata.model.Wod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;

import java.util.List;

@Component
public class StatisticsService {

    @Autowired
    private MongoTemplate mongoTemplate;
    Query query = new Query();


    public long obtenerRecuentoTotal() {
        return mongoTemplate.count(query, Wod.class);
    }

    public String obtenerMovimientoMasRepetido() {
        // Operación de agregación para contar la frecuencia de cada movimiento
        GroupOperation groupOperation = Aggregation.group("bloques.ejercicios.mov").count().as("count");

        // Ordenar por la cuenta en orden descendente
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("bloques"),
                Aggregation.unwind("bloques.ejercicios"),
                groupOperation,
                Aggregation.match(Criteria.where("_id").ne(null)), // Filtrar movimientos nulos
                Aggregation.sort(Sort.by(Sort.Order.desc("count"))),
                Aggregation.limit(1) // Obtener el primero (el más repetido)
        );

        // Ejecutar la agregación
        AggregationResults<MostRepeatedMovementResult> results =
                mongoTemplate.aggregate(aggregation, "wod", MostRepeatedMovementResult.class);

        // Obtener el resultado
        List<MostRepeatedMovementResult> mappedResults = results.getMappedResults();

        if (!mappedResults.isEmpty()) {
            return mappedResults.get(0).getId();
        } else {
            return null; // No se encontraron movimientos repetidos
        }

    }

    // Clase auxiliar para mapear el resultado de la agregación
    private static class MostRepeatedMovementResult {
        private String id;

        public String getId() {
            return id;
        }
    }
    public List<String> obtenerTodosLosMovimientos() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("bloques"),
                Aggregation.unwind("bloques.ejercicios"),
                Aggregation.group("bloques.ejercicios.mov").first("bloques.ejercicios.mov").as("movimiento")
        );

        AggregationResults<MovimientoResult> results =
                mongoTemplate.aggregate(aggregation, "wod", MovimientoResult.class);

        List<MovimientoResult> mappedResults = results.getMappedResults();

        return mappedResults.stream()
                .map(MovimientoResult::getMovimiento)
                .toList();
    }


    private static class MovimientoResult {
        private String movimiento;

        public String getMovimiento() {
            return movimiento;
        }
    }

    public List<Wod> buscarRegistrosConPushUp(String mov) {
        // Crear una consulta para buscar documentos que contengan "Push Up" en algún atributo "mov"
        Criteria criteria = Criteria.where("bloques.ejercicios.mov").is(mov);
        Query query = new Query(criteria);

        // Ejecutar la consulta y obtener los resultados
        return mongoTemplate.find(query, Wod.class);
    }

    }






