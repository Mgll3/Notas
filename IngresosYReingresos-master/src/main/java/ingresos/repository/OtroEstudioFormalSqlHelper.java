package ingresos.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class OtroEstudioFormalSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("institucion", table, columnPrefix + "_institucion"));
        columns.add(Column.aliased("estudio", table, columnPrefix + "_estudio"));
        columns.add(Column.aliased("graduado", table, columnPrefix + "_graduado"));
        columns.add(Column.aliased("year", table, columnPrefix + "_year"));
        columns.add(Column.aliased("titulo_obtenido", table, columnPrefix + "_titulo_obtenido"));
        columns.add(Column.aliased("nivel", table, columnPrefix + "_nivel"));

        columns.add(Column.aliased("otro_formal_id", table, columnPrefix + "_otro_formal_id"));
        return columns;
    }
}
