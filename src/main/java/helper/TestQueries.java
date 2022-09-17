package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class TestQueries {

    public static String searchUsers(int userId) throws SQLException {
        String sql = "SELECT * FROM client_schedule.users WHERE User_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        String userName = "";
        ps.setInt(1, userId);
        ResultSet results = ps.executeQuery();


        // LOOP through results Array
        while(results.next()) {
            userName = results.getString("User_Name");
        }

        if(userName == ""){
            return "User not found";
        }
        else{
            return userName;
        }
    }

}
