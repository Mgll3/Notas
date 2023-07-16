package ingresos.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class MatriculaSemestreSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("tipo_ingreso", table, columnPrefix + "_tipo_ingreso"));
        columns.add(Column.aliased("tipo_aspirante", table, columnPrefix + "_tipo_aspirante"));
        columns.add(Column.aliased("version", table, columnPrefix + "_version"));
        columns.add(Column.aliased("modalidad", table, columnPrefix + "_modalidad"));
        columns.add(Column.aliased("sede", table, columnPrefix + "_sede"));
        columns.add(Column.aliased("year", table, columnPrefix + "_year"));
        columns.add(Column.aliased("cohorte", table, columnPrefix + "_cohorte"));
        columns.add(Column.aliased("estado", table, columnPrefix + "_estado"));
        columns.add(Column.aliased("promedio_semestre", table, columnPrefix + "_promedio_semestre"));
        columns.add(Column.aliased("promedio_acumulado", table, columnPrefix + "_promedio_acumulado"));
        columns.add(Column.aliased("liquidacion_matricula", table, columnPrefix + "_liquidacion_matricula"));
        columns.add(Column.aliased("estado_pago", table, columnPrefix + "_estado_pago"));
        columns.add(Column.aliased("situacion_academica", table, columnPrefix + "_situacion_academica"));

        columns.add(Column.aliased("matriculas_id", table, columnPrefix + "_matriculas_id"));
        columns.add(Column.aliased("matriculaa_id", table, columnPrefix + "_matriculaa_id"));
        return columns;
    }
}
