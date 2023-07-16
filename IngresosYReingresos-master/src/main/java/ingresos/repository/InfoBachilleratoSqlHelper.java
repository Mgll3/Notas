package ingresos.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class InfoBachilleratoSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("ciudad", table, columnPrefix + "_ciudad"));
        columns.add(Column.aliased("departamento", table, columnPrefix + "_departamento"));
        columns.add(Column.aliased("pais", table, columnPrefix + "_pais"));
        columns.add(Column.aliased("colegio", table, columnPrefix + "_colegio"));
        columns.add(Column.aliased("year", table, columnPrefix + "_year"));
        columns.add(Column.aliased("modalidad", table, columnPrefix + "_modalidad"));
        columns.add(Column.aliased("puntaje_icfes", table, columnPrefix + "_puntaje_icfes"));

        return columns;
    }
}
