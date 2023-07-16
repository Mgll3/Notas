package ingresos.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class InfoPersonalSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("tipo_id", table, columnPrefix + "_tipo_id"));
        columns.add(Column.aliased("nombre", table, columnPrefix + "_nombre"));
        columns.add(Column.aliased("apellidos", table, columnPrefix + "_apellidos"));
        columns.add(Column.aliased("genero", table, columnPrefix + "_genero"));
        columns.add(Column.aliased("fecha_nacimiento", table, columnPrefix + "_fecha_nacimiento"));
        columns.add(Column.aliased("edad_ingreso", table, columnPrefix + "_edad_ingreso"));
        columns.add(Column.aliased("telefono", table, columnPrefix + "_telefono"));
        columns.add(Column.aliased("celular", table, columnPrefix + "_celular"));
        columns.add(Column.aliased("correo_personal", table, columnPrefix + "_correo_personal"));
        columns.add(Column.aliased("nacionalidad", table, columnPrefix + "_nacionalidad"));

        return columns;
    }
}
