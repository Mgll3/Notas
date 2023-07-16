package ingresos.repository.rowmapper;

import ingresos.domain.Programa;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Programa}, with proper type conversions.
 */
@Service
public class ProgramaRowMapper implements BiFunction<Row, String, Programa> {

    private final ColumnConverter converter;

    public ProgramaRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Programa} stored in the database.
     */
    @Override
    public Programa apply(Row row, String prefix) {
        Programa entity = new Programa();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCodigo(converter.fromRow(row, prefix + "_codigo", String.class));
        entity.setNombre(converter.fromRow(row, prefix + "_nombre", String.class));
        entity.setRestriccion(converter.fromRow(row, prefix + "_restriccion", String.class));
        entity.setDescripcion(converter.fromRow(row, prefix + "_descripcion", String.class));
        return entity;
    }
}
