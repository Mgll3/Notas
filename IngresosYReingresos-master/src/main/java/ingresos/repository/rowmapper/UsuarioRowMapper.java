package ingresos.repository.rowmapper;

import ingresos.domain.Usuario;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Usuario}, with proper type conversions.
 */
@Service
public class UsuarioRowMapper implements BiFunction<Row, String, Usuario> {

    private final ColumnConverter converter;

    public UsuarioRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Usuario} stored in the database.
     */
    @Override
    public Usuario apply(Row row, String prefix) {
        Usuario entity = new Usuario();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setUsuario(converter.fromRow(row, prefix + "_usuario", String.class));
        entity.setPassword(converter.fromRow(row, prefix + "_password", String.class));
        entity.setCorreoInstitucional(converter.fromRow(row, prefix + "_correo_institucional", String.class));
        entity.setRol(converter.fromRow(row, prefix + "_rol", String.class));
        return entity;
    }
}
