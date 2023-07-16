package ingresos.repository.rowmapper;

import ingresos.domain.Estudiante;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Estudiante}, with proper type conversions.
 */
@Service
public class EstudianteRowMapper implements BiFunction<Row, String, Estudiante> {

    private final ColumnConverter converter;

    public EstudianteRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Estudiante} stored in the database.
     */
    @Override
    public Estudiante apply(Row row, String prefix) {
        Estudiante entity = new Estudiante();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setDiligenciado(converter.fromRow(row, prefix + "_diligenciado", Boolean.class));
        entity.setEstudiantePId(converter.fromRow(row, prefix + "_estudiantep_id", String.class));
        entity.setEstudianteEId(converter.fromRow(row, prefix + "_estudiantee_id", Long.class));
        entity.setEstudianteAId(converter.fromRow(row, prefix + "_estudiantea_id", Long.class));
        return entity;
    }
}
