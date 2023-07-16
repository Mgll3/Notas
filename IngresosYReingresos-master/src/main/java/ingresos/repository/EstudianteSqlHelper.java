package ingresos.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class EstudianteSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("diligenciado", table, columnPrefix + "_diligenciado"));

        columns.add(Column.aliased("estudiantep_id", table, columnPrefix + "_estudiantep_id"));
        columns.add(Column.aliased("estudiantee_id", table, columnPrefix + "_estudiantee_id"));
        columns.add(Column.aliased("estudiantea_id", table, columnPrefix + "_estudiantea_id"));
        return columns;
    }
}
