package stc.inno;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main2 {
    public static void main(String[] args) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/jdbcDB", "postgres",
                                                                 "qwerty");
             Statement pstmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE,
                                                          ResultSet.CLOSE_CURSORS_AT_COMMIT)) {
            DBUtil.renewDatabase();
            connection.setAutoCommit(false);

            try (ResultSet rs = pstmt.executeQuery("select * from mobile")) {
                while (rs.next()) {
                    if (rs.getString("model").equalsIgnoreCase("FRY1")) {
                        rs.updateString("model", "Iphone");
                        rs.updateRow();
                    }
                }

                rs.last();
                rs.deleteRow();

                rs.moveToInsertRow();
                rs.updateString(2, "Razer");
                rs.updateInt(3, 96000);
                rs.updateString(4, "Motorola");
                rs.insertRow();


                connection.commit();
                //rs.close();
                //System.out.println(rs.isClosed());
            }
        }
    }
}
