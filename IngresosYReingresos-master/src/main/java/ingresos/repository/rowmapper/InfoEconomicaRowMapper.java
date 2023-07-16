package ingresos.repository.rowmapper;

import ingresos.domain.InfoEconomica;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link InfoEconomica}, with proper type conversions.
 */
@Service
public class InfoEconomicaRowMapper implements BiFunction<Row, String, InfoEconomica> {

    private final ColumnConverter converter;

    public InfoEconomicaRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link InfoEconomica} stored in the database.
     */
    @Override
    public InfoEconomica apply(Row row, String prefix) {
        InfoEconomica entity = new InfoEconomica();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setDireccion(converter.fromRow(row, prefix + "_direccion", String.class));
        entity.setBarrio(converter.fromRow(row, prefix + "_barrio", String.class));
        entity.setCiudad(converter.fromRow(row, prefix + "_ciudad", String.class));
        entity.setDepartmento(converter.fromRow(row, prefix + "_departmento", String.class));
        entity.setEstrato(converter.fromRow(row, prefix + "_estrato", Integer.class));
        entity.setDependeEcono(converter.fromRow(row, prefix + "_depende_econo", Boolean.class));
        entity.setTrabaja(converter.fromRow(row, prefix + "_trabaja", Boolean.class));
        entity.setTieneDependientes(converter.fromRow(row, prefix + "_tiene_dependientes", Boolean.class));
        return entity;
    }
}
