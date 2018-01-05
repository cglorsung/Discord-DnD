package discord.bots;

import java.sql.*;

/**
 * Created by Conor on 12/14/2017.
 */
public class PopulateDB {

    PopulateDB(){}

    public static void main(String[] args) {
        System.out.println(createSystem());
    }

    public static boolean createSystem() {
        boolean weaponBool = false;
        boolean armorBool = false;
        boolean userBool = false;

        try {
            Connection conn = OpenDBConnection.connect();
            ResultSet rs;
            PreparedStatement pstmt;

            String query = "SELECT * FROM USERS";

            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                System.out.println(rs.getString("name"));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
