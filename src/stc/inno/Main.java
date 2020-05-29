package stc.inno;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/jdbcDB", "postgres", "qwerty")) {
            DBUtil.renewDatabase();

            databaseMetadataEx(connection);
            // typeInfoEx(connection);
            // resultSetMetadataEx(connection);
        }
    }

    private static void typeInfoEx(Connection connection) throws SQLException {
        // Метаданные текущего подключения
        DatabaseMetaData metaData = connection.getMetaData();
        // Набор данных поддерживаемых типов
        ResultSet rs = metaData.getTypeInfo();
        System.out.println("Набор примитивных типов, поддерживаемых данным типом приложения\n");
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    private static void databaseMetadataEx(Connection connection) throws SQLException {
        // Метаданные текущего подключения
        DatabaseMetaData metaData = connection.getMetaData();
        System.out.println(
                "Database: " + metaData.getDatabaseProductName() + " " + metaData.getDatabaseProductVersion());
        System.out.println("Driver: " + metaData.getDriverName() + " " + metaData.getDriverVersion());
        System.out.println("Username" + metaData.getUserName());
        System.out.println("MaxConnection: " + metaData.getMaxConnections());
        System.out.println(
                "TYPE_FORWARD_ONLY resultSet: " + metaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY));
        System.out.println("TYPE_SCROLL_INSENSITIVE resultSet: " + metaData
                .supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE));
        System.out.println(
                "TYPE_SCROLL_SENSITIVE resultSet: " + metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE));
        System.out.println("CONCUR_READ_ONLY resultSet: " + metaData
                .supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY));
        System.out.println("CONCUR_UPDATABLE resultSet: " + metaData
                .supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE));
        System.out.println("CLOSE_CURSORS_AT_COMMIT resultSet: " + metaData
                .supportsResultSetHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT));
        System.out.println("HOLD_CURSORS_OVER_COMMIT resultSet: " + metaData
                .supportsResultSetHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT));
        System.out.println("\n");
    }

    public static void resultSetMetadataEx(Connection connection) throws SQLException {
        Statement         statement   = connection.createStatement();
        ResultSet         resultSet   = statement.executeQuery("SELECT * FROM mobile");
        ResultSetMetaData metaData1   = resultSet.getMetaData();
        int               columnCount = metaData1.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(metaData1.getTableName(i));
            System.out.println(metaData1.getSchemaName(i));
            System.out.println(metaData1.getColumnName(i));
            System.out.println(metaData1.getColumnDisplaySize(i));
        }
    }
}
