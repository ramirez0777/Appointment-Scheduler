package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class queryUsers {

    public static boolean verifyLogin(String username, String password) throws SQLException {
        boolean verified = false;
        JDBC.openConnection();
        String sql = "SELECT * FROM client_schedule.users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while(results.next()){
            if(username.equals(results.getString("User_Name"))){
                if(password.equals(results.getString("Password"))){
                    verified = true;
                    break;
                }
            }
        }

        JDBC.closeConnection();
        return verified;
    }
}
