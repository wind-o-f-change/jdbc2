package stc.inno;

import javax.sql.RowSet;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.WebRowSet;
import java.io.IOException;
import java.sql.SQLException;

public class Main3 {
    public static void main(String[] args) throws SQLException, IOException {
        try (WebRowSet jdbcRS = RowSetProvider.newFactory().createWebRowSet()) {
            jdbcRS.setUrl("jdbc:postgresql://localhost:5433/jdbcDB");
            jdbcRS.setUsername("postgres");
            jdbcRS.setPassword("qwerty");

            DBUtil.renewDatabase();

            jdbcRS.setConcurrency(RowSet.CONCUR_UPDATABLE);
            jdbcRS.addRowSetListener(new ExampleListener());

            String sql = "SELECT * FROM mobile";
            jdbcRS.setCommand(sql);
            jdbcRS.execute();
            while (jdbcRS.next()) {
                if (jdbcRS.getString("model").equalsIgnoreCase("FRY1")) {
                    jdbcRS.updateString("model", "FRY2");
                    jdbcRS.updateRow();
                }
            }

            jdbcRS.writeXml(System.out);
        }
    }
}
