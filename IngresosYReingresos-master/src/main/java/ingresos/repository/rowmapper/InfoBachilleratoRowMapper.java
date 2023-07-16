package ingresos.repository.rowmapper;

import ingresos.domain.InfoBachillerato;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link InfoBachillerato}, with proper type conversions.
 */
@Service
public class InfoBachilleratoRowMapper implements BiFunction<Row, String, InfoBachillerato> {

    private final ColumnConverter converter;

    public InfoBachilleratoRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link InfoBachillerato} stored in the database.
     */
    @Override
    public InfoBachillerato apply(Row row, String prefix) {
        InfoBachillerato entity = new InfoBachillerato();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCiudad(converter.fromRow(row, prefix + "_ciudad", String.class));
        entity.setDepartamento(converter.fromRow(row, prefix + "_departamento", String.class));
        entity.setPais(converter.fromRow(row, prefix + "_pais", String.class));
        entity.setColegio(converter.fromRow(row, prefix + "_colegio", String.class));
        entity.setYear(converter.fromRow(row, prefix + "_year", Integer.class));
        entity.setModalidad(converter.fromRow(row, prefix + "_modalidad", String.class));
        entity.setPuntajeIcfes(converter.fromRow(row, prefix + "_puntaje_icfes", Integer.class));
        return entity;
    }
}
