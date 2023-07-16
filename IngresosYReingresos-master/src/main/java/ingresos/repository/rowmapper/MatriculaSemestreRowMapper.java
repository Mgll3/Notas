package ingresos.repository.rowmapper;

import ingresos.domain.MatriculaSemestre;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link MatriculaSemestre}, with proper type conversions.
 */
@Service
public class MatriculaSemestreRowMapper implements BiFunction<Row, String, MatriculaSemestre> {

    private final ColumnConverter converter;

    public MatriculaSemestreRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link MatriculaSemestre} stored in the database.
     */
    @Override
    public MatriculaSemestre apply(Row row, String prefix) {
        MatriculaSemestre entity = new MatriculaSemestre();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setTipoIngreso(converter.fromRow(row, prefix + "_tipo_ingreso", String.class));
        entity.setTipoAspirante(converter.fromRow(row, prefix + "_tipo_aspirante", String.class));
        entity.setVersion(converter.fromRow(row, prefix + "_version", String.class));
        entity.setModalidad(converter.fromRow(row, prefix + "_modalidad", String.class));
        entity.setSede(converter.fromRow(row, prefix + "_sede", String.class));
        entity.setYear(converter.fromRow(row, prefix + "_year", Integer.class));
        entity.setCohorte(converter.fromRow(row, prefix + "_cohorte", String.class));
        entity.setEstado(converter.fromRow(row, prefix + "_estado", String.class));
        entity.setPromedioSemestre(converter.fromRow(row, prefix + "_promedio_semestre", Double.class));
        entity.setPromedioAcumulado(converter.fromRow(row, prefix + "_promedio_acumulado", Double.class));
        entity.setLiquidacionMatricula(converter.fromRow(row, prefix + "_liquidacion_matricula", String.class));
        entity.setEstadoPago(converter.fromRow(row, prefix + "_estado_pago", String.class));
        entity.setSituacionAcademica(converter.fromRow(row, prefix + "_situacion_academica", String.class));
        entity.setMatriculaSId(converter.fromRow(row, prefix + "_matriculas_id", Long.class));
        entity.setMatriculaAId(converter.fromRow(row, prefix + "_matriculaa_id", Long.class));
        return entity;
    }
}
