package Control;

import Model.*;
import Model.ClassDiagramModel.*;

import java.sql.*;
import java.util.ArrayList;


/**
 * Created by skrud on 2017-10-01.
 */
public class DBController {
    private static final String DRIVER
            = "oracle.jdbc.driver.OracleDriver";
    private final String URL
            = "jdbc:oracle:thin:@localhost:1521:orcl";
    private final String USER = "ksna";
    private final String PW = "ksna";

    private Connection con = null;
    private int memNo;

    public DBController() {
        con = getConn();
        memNo = 0;
    }

    private Connection getConn() {
        Connection con = null;

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PW);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public boolean signup(LoginModel dbdata) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = true;
        try {
            sql = "INSERT INTO MEMBERS (MEMNO, id, password, name, email, reg_date)" +
                    "VALUES (MEMNO.nextval,?, ?, ?, ?, SYSDATE)";
            ps = con.prepareStatement(sql);
            ps.setString(1,dbdata.getId());
            ps.setString(2,dbdata.getPw());
            ps.setString(3,dbdata.getName());
            ps.setString(4,dbdata.getEmail());
            rs = ps.executeQuery();
        } catch (SQLException e) {
            rt = false;
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public LoginModel loginDB(LoginModel dbdata) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "";
        LoginModel rt = new LoginModel();
        try {
            sql = "SELECT * FROM MEMBERS WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dbdata.getId());
            rs = ps.executeQuery();


            if (rs.next()) {
                if (!(rs.getString("id").equals(""))) { //아이디가 같으면
                    memNo = rs.getInt(1);
                    rt.setId(rs.getString("id"));
                    rt.setPw(rs.getString("password"));
                    rt.setEmail(rs.getString("email"));
                    rt.setName(rs.getString("name"));
                }
            }
        } catch (
                SQLException e)

        {
            e.printStackTrace();
        } finally

        {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public boolean pwDB(LoginModel dbdata) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = true;
        try {
            sql = "UPDATE MEMBERS SET PASSWORD = ? WHERE ID = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, dbdata.getPw());
            ps.setString(2, dbdata.getId());
            rs = ps.executeQuery();
        } catch (SQLException e) {
            rt = false;
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public boolean overLapDB(String id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";
        boolean rt = true;
        try {
            sql = "SELECT id FROM MEMBERS WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                rt = false;//""이면 중복아님
            }
        } catch (
                SQLException e)

        {
            e.printStackTrace();
        } finally

        {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public boolean push(CDModel cdm) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Boolean rt = true;

        try {
            String sql = "INSERT INTO CD (CDNO, CDNAME,JSON,MEMBERNO, REG_DATE)" + // 권한추가 필요.
                    "VALUES (CDNO.nextval, ?,?,?,SYSDATE)";
            ps = con.prepareStatement(sql);
            ps.setString(1, cdm.getCdName());
            ps.setString(2, cdm.getJson());
            ps.setInt(3, memNo);

            rs = ps.executeQuery();
        } catch (SQLException e) {
            rt = false;
            e.printStackTrace();
        } finally

        {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public ArrayList<SearchModel> search(SearchPacket sm) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";

        ArrayList<SearchModel> sdms = new ArrayList<>();
        String data = sm.getData();
        String cate = sm.getCategory();

        try {
            data = data.replace("!", "!!")
                    .replace("%", "!%")
                    .replace("_", "!_")
                    .replace("[", "![");
            if (cate.equals("Title")) {
                sql = "SELECT CDNAME,id,TO_CHAR(CD.reg_date,'yyyy-mm-dd hh24:mi:ss') r_date, cd.CDNO\n" +
                        "FROM CD, MEMBERS\n" +
                        "WHERE CD.memberNo = MEMBERS.memNO AND\n" +
                        "  cdName LIKE ? ESCAPE '!'";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + data + "%");
            } else if (cate.equals("UserID")) {
                sql = "SELECT CDNAME,id,TO_CHAR(CD.reg_date,'yyyy-mm-dd hh24:mi:ss') r_date, cd.CDNO\n" +
                        "FROM CD, MEMBERS\n" +
                        "WHERE CD.memberNo = MEMBERS.memNO AND\n" +
                        "MEMBERS.ID = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, data);
            }
            rs = ps.executeQuery();

            SearchModel sdm = null;
            while (rs.next()) {
                sdm = new SearchModel(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                sdms.add(sdm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return sdms;
    }

    public String cllone(CllonePacket cp) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int cdNo = Integer.parseInt(cp.getCdNo());
        String rt = "";

        try {
            String sql = "SELECT cd.json\n" +
                    "FROM CD cd\n" +
                    "WHERE cd.cdNO = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, cdNo);
            rs = ps.executeQuery();
            if (rs.next()) {
                rt = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public boolean searchId(String id) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = true;
        try {
            sql = "SELECT id\n" +
                    "FROM MEMBERS\n" +
                    "WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) rt = false;
        } catch (SQLException e) {
            rt = false;
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }

    public boolean insertFriend(String id) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = true;
        try {
            sql = "INSERT INTO Friends (frNo, id, REG_DATE, memNo)\n" +
                    "VALUES (frNo.nextval,?, SYSDATE, ?);";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setInt(2, memNo);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            rt = false;
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;

    }


    public void closeConnect() {
        try {
            con.close();
        } catch (Exception con) {

        }
    }

    public ArrayList<String> getFriends() {
        ArrayList<String> friends = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = true;
        try {
            sql = "SELECT *\n" +
                    "FROM FRIENDS\n" +
                    "WHERE MEMNO = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, memNo);
            rs = ps.executeQuery();
            while(rs.next()){
                friends.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            rt = false;
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (Exception rse) {
            }
            try {
                ps.close();
            } catch (Exception pse) {
            }
        }
        return friends;

    }
}
