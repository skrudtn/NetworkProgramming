package Control;

import Model.*;
import Model.ClassDiagramModel.*;
import Model.RepoModel.*;
import Model.VersionModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;


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
    private String myId;

    public DBController() {
        con = getConn();
        memNo = 0;
        myId = "";
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

    public void closeConnect() {
        try {
            con.close();
        } catch (Exception con) {

        }
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
            ps.setString(1, dbdata.getId());
            ps.setString(2, dbdata.getPw());
            ps.setString(3, dbdata.getName());
            ps.setString(4, dbdata.getEmail());
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
                    myId = rt.getId();
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

    public boolean pushCD(CDModel cdm, int verNo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Boolean rt = true;

        try {

            String sql = "INSERT INTO CD (CDNO, JSON, VERNO, REG_DATE)" +
                    "VALUES (CDNO.nextval, ?, ?, SYSDATE)";
            ps = con.prepareStatement(sql);
            ps.setString(1, cdm.getJson());
            ps.setInt(2, verNo);

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
                sql = "SELECT REPO.NAME,ID,TO_CHAR(REPO.reg_date,'yyyy-mm-dd hh24:mi:ss') r_date, REPONO\n" +
                        "FROM REPO, MEMBERS\n" +
                        "WHERE REPO.MEMNO = MEMBERS.memNO AND\n" +
                        "  REPO.NAME LIKE ? ESCAPE '!'";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + data + "%");
            } else if (cate.equals("UserID")) {
                sql = "SELECT REPO.NAME,ID,TO_CHAR(REPO.reg_date,'yyyy-mm-dd hh24:mi:ss') r_date, REPONO\n" +
                        "FROM REPO, MEMBERS\n" +
                        "WHERE REPO.MEMNO = MEMBERS.memNO AND\n" +
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
        String name = cp.getName();
        String rt = "";

        try {
            String sql = "SELECT cd.json\n" +
                    "FROM CD cd, VERSIONS v\n" +
                    "WHERE cd.verNo = v.verNO\n" +
                    "AND v.name = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
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

    public boolean selectId(String id) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = false;
        try {
            sql = "SELECT id\n" +
                    "FROM MEMBERS\n" +
                    "WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString(1));
                rt = true;
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
        return rt;
    }

    void insertFriend(String id) {
        System.out.println("부적합?" + id);
        System.out.println(memNo);
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = true;
        try {
            sql = "SELECT m.memNO\n" +
                    "  FROM MEMBERS m\n" +
                    "WHERE m.id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            int mNo = 0;
            if (rs.next()) {
                mNo = rs.getInt(1);
            }

            sql = "INSERT INTO Friends (frNo, id, REG_DATE, memNo)\n" +
                    "VALUES (frNo.nextval,?, SYSDATE, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, myId);
            ps.setInt(2, mNo);
            rs = ps.executeQuery();

            sql = "INSERT INTO Friends (frNo, id, REG_DATE, memNo)\n" +
                    "VALUES (frNo.nextval,?, SYSDATE, ?)";
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

    }

    ArrayList<String> getFriends(String id) {
        ArrayList<String> friends = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = true;
        try {
            sql = "SELECT f.ID\n" +
                    "FROM FRIENDS f, MEMBERS m\n" +
                    "WHERE f.MEMNO = m.MEMNO\n" +
                    "AND m.ID = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                friends.add(rs.getString(1));
            }
            System.out.println();
            System.out.println(myId + "의 친구" + friends + "\n");
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

    ArrayList<String> getFriends() {
        ArrayList<String> friends = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        System.out.println("내 memNo" + memNo);
        try {
            sql = "SELECT ID\n" +
                    "FROM FRIENDS\n" +
                    "WHERE MEMNO = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, memNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                friends.add(rs.getString(1));
            }
            System.out.println();
            System.out.println(myId + "의 친구" + friends + "\n");
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
        return friends;
    }

    boolean isExistRepo(String repo) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = false;
        try {
            sql = "SELECT REPONO\n" +
                    "FROM REPO\n" +
                    "WHERE REPO.NAME = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, repo);

            rs = ps.executeQuery();
            if (rs.next()) {
                rt = true;
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
        return rt;
    }

    public RepoModel selectRepoData(RepoModel repo) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        RepoModel repoModel = new RepoModel();
        ArrayList<VersionModel> versions = new ArrayList<>();
        try {
            sql = "SELECT v.verNO, v.name, v.MODIFIEDBY, TO_CHAR(v.reg_date,'yyyy-mm-dd hh24:mi:ss'), r.CREATEBY, v.VER\n" +
                    "FROM REPO r, VERSIONS v\n" +
                    "WHERE r.repoNo = v.repoNo\n" +
                    "  and r.REPONO = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, repo.getRepoNo());
            rs = ps.executeQuery();
            while (rs.next()) {
                VersionModel v = new VersionModel();
                v.setVerNo(rs.getInt(1));
                v.setName(rs.getString(2));
                v.setModifiedBy(rs.getString(3));
                v.setReg_date(rs.getString(4));
                v.setCreateBy(rs.getString(5));
                v.setVer(rs.getInt(6));
                versions.add(v);
            }
            repoModel.setRepoNo(repo.getRepoNo());
            repoModel.setName(repo.getName());
            repoModel.setId(repo.getId());
            repoModel.setVersions(versions);

            sql = "SELECT m.id\n" +
                    "  FROM AUTHO a, REPO r, MEMBERS m\n" +
                    "WHERE a.repoNo = r.repoNo\n" +
                    "AND m.memNO = a.memNo\n" +
                    "AND r.REPONO= ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, repo.getRepoNo());
            rs = ps.executeQuery();
            System.out.println("멤버보기");
            while (rs.next()) {
                System.out.println(rs.getString(1));
                repoModel.addAuthorization(rs.getString(1));
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
        return repoModel;
    }

    public int insertRepo(RepoPacket rp) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        int repoNo = 0;
        try {
            sql = "INSERT INTO REPO(REPONO,NAME,MEMNO,CREATEBY,REG_DATE)" +
                    "VALUES (REPONO.nextval,?,?,?,SYSDATE)";
            ps = con.prepareStatement(sql);
            ps.setString(1, rp.getName());
            ps.setInt(2, memNo);
            ps.setString(3, rp.getId());
            rs = ps.executeQuery();

            sql = "SELECT REPONO.currval FROM dual";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                repoNo = rs.getInt(1);
                for (String str : rp.getAuthorities()) {
                    sql = "SELECT MEMNO\n" +
                            "FROM MEMBERS\n" +
                            "WHERE ID = ?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, str);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        int mNo = rs.getInt(1);
                        sql = "INSERT INTO AUTHO(AUTHONO, MEMNO, REPONO)" +
                                "VALUES (AUTHONO.nextval, ?,?)";
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, mNo);
                        ps.setInt(2, repoNo);
                        rs = ps.executeQuery();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert rs != null;
                rs.close();
            } catch (Exception rse) {
            }
            try {
                assert ps != null;
                ps.close();
            } catch (Exception pse) {
            }
        }
        return repoNo;
    }

    public int insertVersion(String repo, String id, int repoNo) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        int verNo = 0;
        try {
            int ver = 0;
            sql = "SELECT Max(v.ver)\n" +
                    "  FROM VERSIONS v\n" +
                    "WHERE v.repoNo =?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, repoNo);
            rs = ps.executeQuery();
            if (rs.next()) {
                ver = rs.getInt(1);
                if (rs.getInt(1) == 0) {
                    ver = 1;
                } else {
                    ver++;
                }
            }

            sql = "INSERT INTO VERSIONS(VERNO, NAME, MODIFIEDBY, VER, REPONO, REG_DATE) " +
                    "VALUES (VERNO.nextval,?,?, ?, ?, SYSDATE)";
            ps = con.prepareStatement(sql);
            ps.setString(1, repo + "_ver" + String.valueOf(ver));
            ps.setString(2, id);
            ps.setInt(3, ver);
            ps.setInt(4, repoNo);
            rs = ps.executeQuery();

            sql = "SELECT VERNO.currval FROM dual";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            verNo = 0;
            if (rs.next()) {
                verNo = rs.getInt(1);
            }

        } catch (
                SQLException e)

        {
            e.printStackTrace();
        } finally

        {
            try {
                assert rs != null;
                rs.close();
            } catch (Exception rse) {
            }
            try {
                assert ps != null;
                ps.close();
            } catch (Exception pse) {
            }
        }
        return verNo;
    }

    public boolean updateAutho(MemManagePacket mmp) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "";
        boolean rt = true;
        try {
            ArrayList<String> members = mmp.getMembers();
            int repoNo = mmp.getRepoNo();

            sql = "DELETE FROM AUTHO\n" +
                    "WHERE repoNo = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, repoNo);
            rs = ps.executeQuery();

            for (String str : members) {
                int memNo = 0;
                sql = "SELECT memNo\n" +
                        "  FROm MEMBERS\n" +
                        "WHERE id = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, str);
                rs = ps.executeQuery();
                if (rs.next()) memNo = rs.getInt(1);

                sql = "INSERT INTO AUTHO(authoNO, memNO, repoNo)\n" +
                        "    VALUES (authoNO.nextval, ?,?)";
                ps = con.prepareStatement(sql);
                System.out.println(memNo);
                System.out.println(repoNo);
                ps.setInt(1, memNo);
                ps.setInt(2, repoNo);
                rs = ps.executeQuery();
            }
        } catch (
                SQLException e)

        {
            rt = false;
            e.printStackTrace();
        } finally

        {
            try {
                assert rs != null;
                rs.close();
            } catch (Exception rse) {
            }
            try {
                assert ps != null;
                ps.close();
            } catch (Exception pse) {
            }
        }
        return rt;
    }
}