package Framework;

import java.sql.*;

public class DataBase { //SINGLETON
    private static DataBase bddInstance = null; //instanta privata
    private DataBase(Connection c) throws SQLException {    //constructor privat
        createTable(c);
    }

    public static DataBase getInstance(Connection c) throws SQLException {  //getInstance
        if(bddInstance == null)
            bddInstance = new DataBase(c);
        return bddInstance;
    }

    public static void createTable(Connection c) throws SQLException {
        String command = "CREATE TABLE GAME" +
                "( nrCoin INT NOT NULL, "+
                "level INT NOT NULL, "+
                "nrMaxCoins INT NOT NULL, "+
                "score INT NOT NULL )";
        Statement stmt = c.createStatement();
        stmt.execute(command);
    }

    public static void deleteTable(Connection c) throws SQLException {
        String sql = "DROP TABLE Scor";
        Statement stmt = c.createStatement();
        stmt.execute(sql);
    }

    public static void insertRecord(Connection c, int nrCoin, int level, int nrMaxCoin, int score) throws SQLException {
        String sql = "INSERT INTO GAME " +
                "VALUES (?,?,?,?);";
        PreparedStatement pstmt = c.prepareStatement(sql);

        pstmt.setInt(1, nrCoin);
        pstmt.setInt(2, level);
        pstmt.setInt(3, nrMaxCoin);
        pstmt.setInt(4, score);

        pstmt.executeUpdate();
    }
}
