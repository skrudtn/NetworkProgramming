package DB;

/**
 * Created by skrud on 2017-11-03.
 */

import java.sql.*;

public class booklist {
    Connection con;

    public booklist() {
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        /* 11g express edition은 orcl 대신 XE를 입력한다. */
        String userid = "madang";
        String pwd = "madang";

        try { /* 드라이버를 찾는 과정 */
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try { /* 데이터베이스를 연결하는 과정 */
            System.out.println("데이터베이스 연결 준비 ...");
            con = DriverManager.getConnection(url, userid, pwd);
            System.out.println("데이터베이스 연결 성공");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sqlRun() {

        String query = "SELECT * FROM BOOK"; /* SQL 문 */
        try { /* 데이터베이스에 질의 결과를 가져오는 과정 */
            // Select Book Table
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("BOOK NO\tBOOK NAME \t\t\t\tPUBLISHER \t\tPRICE");
            while (rs.next()) {
                System.out.print("\t" + rs.getInt(1));
                System.out.print("\t" + rs.getString(2));
                System.out.print("\t\t\t\t" + rs.getString(3));
                System.out.println("\t\t\t" + rs.getInt(4));
            }
            System.out.println();

            // SELECT CUSTOMER TABLE
            query = "SELECT * FROM CUSTOMER";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            System.out.println("CUST NO \tCUST NAME \t\tADDRESS\t\t\t\tPHONE");
            while (rs.next()) {
                System.out.print("\t" + rs.getInt(1));
                System.out.print("\t\t" + rs.getString(2));
                System.out.print("\t\t\t" + rs.getString(3));
                System.out.println("\t\t\t\t" + rs.getString(4));
            }
            System.out.println();

            // SELECT ORDERS TABLE
            query = "SELECT * FROM ORDERS";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            System.out.println("ORDER NO \tCUST ID\t\tBOOK ID\t\tSALEPRICE\tDATE");
            while (rs.next()) {
                System.out.print("\t" + rs.getInt(1));
                System.out.print("\t\t" + rs.getInt(2));
                System.out.print("\t\t\t" + rs.getInt(3));
                System.out.print("\t\t\t" + rs.getInt(4));
                System.out.println("\t\t" + rs.getDate(5));
            }

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        booklist so = new booklist();
        so.sqlRun();
    }

}
