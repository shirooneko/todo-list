package grouppbo2;

import java.sql.*;
import java.util.ArrayList;

class user {
    private int id;
    private String username;
    private String password;
    private String role;
    private String randomId;

    user(int id, String username, String password, String role, String randomId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.randomId = randomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRandomId() {
        return randomId;
    }

    public void setRandomId(String randomId) {
        this.randomId = randomId;
    }

    public static ArrayList<user> readUser(){
        Connection conn = null;
        // inisilasi variabel
        ArrayList<user> userArr = new ArrayList<>();
        try{
            //mengoneksikan ke database
            String url = "jdbc:mysql://localhost/grouppbo2?";
            String user = "root";
            String pass = "";

            conn = DriverManager.getConnection(url,user,pass);
            Statement statement1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement1.executeQuery("SELECT * FROM user");

            //memasukan nilai dari database untuk masuk ke dalam array list user supaya dapat dibaca
            int countedID = 0;
            while(result.next()){
                userArr.add(new user(countedID, result.getString(2), result.getString(3),result.getString(4),result.getString(1)));
                countedID++;
            }

        }catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return userArr;
    }


    public static void inputUserDatabase(ArrayList<user> userList){
        Connection conn = null;
        try{
            //mengoneksikan ke database
            String url = "jdbc:mysql://localhost/grouppbo2?";
            String user = "root";
            String pass = "";

            conn = DriverManager.getConnection(url,user,pass);
            PreparedStatement clearTable = conn.prepareStatement("DELETE FROM user");
            clearTable.execute();

            for(user x : userList){
                String query = "insert into user (username, password, role)" + " values (?, ?, ?)";

                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString (1, x.getUsername());
                preparedStmt.setString (2, x.getPassword());
                preparedStmt.setString (3, x.getRole());
                preparedStmt.execute();
            }
        }catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}