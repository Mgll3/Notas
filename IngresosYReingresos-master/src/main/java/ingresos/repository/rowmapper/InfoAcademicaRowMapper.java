package ingresos.repository.rowmapper;

import ingresos.domain.InfoAcademica;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link InfoAcademica}, with proper type conversions.
 */
@Service
public class InfoAcademicaRowMapper implements BiFunction<Row, String, InfoAcademica> {

    private final ColumnConverter converter;

    public InfoAcademicaRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link InfoAcademica} stored in the database.
     */
    @Override
    public InfoAcademica apply(Row row, String prefix) {
        InfoAcademica entity = new InfoAcademica();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setAcademicaId(converter.fromRow(row, prefix + "_academica_id", Long.class));
        return entity;
    }
}
