package stc.inno;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

public class Main4 {
    private static final String INSERT_SQL = "INSERT INTO mobile "
                                             + "(model, price, manufacturer) VALUES (?,?,?)";

    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/jdbcDB", "postgres", "qwerty")) {
            DBUtil.renewDatabase();
            conn.setAutoCommit(false);

            try (PreparedStatement insertStmt = conn.prepareStatement(INSERT_SQL)) {
                // 1 запись
                insertStmt.setString(1, "F1");
                insertStmt.setInt(2, 200);
                insertStmt.setString(3, "OPPO");
                insertStmt.executeUpdate();

                // 2 запись
                insertStmt.setString(1, "F2");
                insertStmt.setInt(2, 400);
                insertStmt.setString(3, "OPPO");
                insertStmt.executeUpdate();

                // 3 запись
                insertStmt.setString(1, "F3");
                insertStmt.setInt(2, 800);
                insertStmt.setString(3, "OPPO");
                insertStmt.executeUpdate();

                // Создание Savepoint
                Savepoint first_savepoint = conn.setSavepoint("first_savepoint");

                // 4 запись
                insertStmt.setString(1, "F1P");
                insertStmt.setInt(2, 11200);
                insertStmt.setString(3, "OPPO");
                insertStmt.executeUpdate();

                // Создание Savepoint
                Savepoint second_savepoint = conn.setSavepoint("second_savepoint");

                // 5 запись
                insertStmt.setString(1, "F2P");
                insertStmt.setInt(2, 21200);
                insertStmt.setString(3, "OPPO");
                insertStmt.executeUpdate();

                conn.releaseSavepoint(second_savepoint);
                // Rollback к savepoint
                conn.rollback(first_savepoint);

                // Commit транзакции
                conn.commit();
            }
        }
    }
}
