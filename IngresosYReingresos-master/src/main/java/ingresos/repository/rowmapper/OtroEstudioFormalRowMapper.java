package ingresos.repository.rowmapper;

import ingresos.domain.OtroEstudioFormal;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link OtroEstudioFormal}, with proper type conversions.
 */
@Service
public class OtroEstudioFormalRowMapper implements BiFunction<Row, String, OtroEstudioFormal> {

    private final ColumnConverter converter;

    public OtroEstudioFormalRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link OtroEstudioFormal} stored in the database.
     */
    @Override
    public OtroEstudioFormal apply(Row row, String prefix) {
        OtroEstudioFormal entity = new OtroEstudioFormal();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setInstitucion(converter.fromRow(row, prefix + "_institucion", String.class));
        entity.setEstudio(converter.fromRow(row, prefix + "_estudio", Long.class));
        entity.setGraduado(converter.fromRow(row, prefix + "_graduado", Boolean.class));
        entity.setYear(converter.fromRow(row, prefix + "_year", Integer.class));
        entity.setTituloObtenido(converter.fromRow(row, prefix + "_titulo_obtenido", String.class));
        entity.setNivel(converter.fromRow(row, prefix + "_nivel", String.class));
        entity.setOtroFormalId(converter.fromRow(row, prefix + "_otro_formal_id", Long.class));
        return entity;
    }
}
