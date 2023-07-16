package ingresos.repository.rowmapper;

import ingresos.domain.InfoPersonal;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link InfoPersonal}, with proper type conversions.
 */
@Service
public class InfoPersonalRowMapper implements BiFunction<Row, String, InfoPersonal> {

    private final ColumnConverter converter;

    public InfoPersonalRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link InfoPersonal} stored in the database.
     */
    @Override
    public InfoPersonal apply(Row row, String prefix) {
        InfoPersonal entity = new InfoPersonal();
        entity.setId(converter.fromRow(row, prefix + "_id", String.class));
        entity.setTipoID(converter.fromRow(row, prefix + "_tipo_id", String.class));
        entity.setNombre(converter.fromRow(row, prefix + "_nombre", String.class));
        entity.setApellidos(converter.fromRow(row, prefix + "_apellidos", String.class));
        entity.setGenero(converter.fromRow(row, prefix + "_genero", String.class));
        entity.setFechaNacimiento(converter.fromRow(row, prefix + "_fecha_nacimiento", String.class));
        entity.setEdadIngreso(converter.fromRow(row, prefix + "_edad_ingreso", Integer.class));
        entity.setTelefono(converter.fromRow(row, prefix + "_telefono", Integer.class));
        entity.setCelular(converter.fromRow(row, prefix + "_celular", Integer.class));
        entity.setCorreoPersonal(converter.fromRow(row, prefix + "_correo_personal", String.class));
        entity.setNacionalidad(converter.fromRow(row, prefix + "_nacionalidad", String.class));
        return entity;
    }
}
