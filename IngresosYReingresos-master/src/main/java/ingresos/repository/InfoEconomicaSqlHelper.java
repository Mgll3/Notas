package ingresos.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class InfoEconomicaSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("direccion", table, columnPrefix + "_direccion"));
        columns.add(Column.aliased("barrio", table, columnPrefix + "_barrio"));
        columns.add(Column.aliased("ciudad", table, columnPrefix + "_ciudad"));
        columns.add(Column.aliased("departmento", table, columnPrefix + "_departmento"));
        columns.add(Column.aliased("estrato", table, columnPrefix + "_estrato"));
        columns.add(Column.aliased("depende_econo", table, columnPrefix + "_depende_econo"));
        columns.add(Column.aliased("trabaja", table, columnPrefix + "_trabaja"));
        columns.add(Column.aliased("tiene_dependientes", table, columnPrefix + "_tiene_dependientes"));

        return columns;
    }
}
