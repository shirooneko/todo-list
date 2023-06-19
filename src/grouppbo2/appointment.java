package grouppbo2;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


class appointment {
    private int id;
    private String description;
    private Date appoinmentDate;
    private Time starttime;
    private Time endtime;
    private boolean state;
    private String createdby;
    private String randomId;

    public appointment(int id, String description, Date appoinmentDate, Time starttime, Time endtime, boolean state, String createdby, String randomId) {
        this.id = id;
        this.description = description;
        this.appoinmentDate = appoinmentDate;
        this.starttime = starttime;
        this.endtime = endtime;
        this.state = state;
        this.createdby = createdby;
        this.randomId = randomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAppoinmentDate() {
        return appoinmentDate;
    }

    public void setAppoinmentDate(Date appoinmentDate) {
        this.appoinmentDate = appoinmentDate;
    }

    public Time getStarttime() {
        return starttime;
    }

    public void setStarttime(Time starttime) {
        this.starttime = starttime;
    }

    public Time getEndtime() {
        return endtime;
    }

    public void setEndtime(Time endtime) {
        this.endtime = endtime;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getRandomId() {
        return randomId;
    }

    public void setRandomId(String randomId) {
        this.randomId = randomId;
    }

    public static ArrayList<appointment> readAppointmentArray() {
        Connection conn = null;
        // Initialize Variables
        ArrayList<appointment> appointmentArr = new ArrayList<>();


        try {
            //mengoneksikan ke database
            String url = "jdbc:mysql://localhost/grouppbo2?";
            String user = "root";
            String pass = "";

            conn = DriverManager.getConnection(url, user, pass);
            Statement statement1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = statement1.executeQuery("SELECT * FROM appointments");


            int countedID = 0;
            while (result.next()) {
                String description = result.getString(2);
                String appoinmentDateStr = result.getString(3);
                String starttimeStr = result.getString(4);
                String endtimeStr = result.getString(5);
                String state = result.getString(6);
                String username = result.getString(7);
                boolean stateBool = state.equals("1");
                try {
                    // Convert Data
                    SimpleDateFormat adt = new SimpleDateFormat("yyyy-MM-dd");
                    Date appoinmentDate = adt.parse(appoinmentDateStr);
                    Time starttime = new Time(new SimpleDateFormat("HH:mm").parse(starttimeStr).getTime());
                    Time endtime = new Time(new SimpleDateFormat("HH:mm").parse(endtimeStr).getTime());
                    appointmentArr.add(new appointment(countedID, description, appoinmentDate, starttime, endtime, stateBool, username, UUID.randomUUID().toString()));
                    countedID++;
                } catch (ParseException e) {
                    System.out.println("Exception: " + e);
                }

            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return appointmentArr;
    }

    public static void inputAppointmentDatabase(ArrayList<appointment> appointmentList) {
        Connection conn = null;
        try {
            //mengoneksikan ke database
            String url = "jdbc:mysql://localhost/grouppbo2?";
            String user = "root";
            String pass = "";

            conn = DriverManager.getConnection(url, user, pass);
            PreparedStatement clearTable = conn.prepareStatement("DELETE FROM appointments");
            clearTable.execute();
            for (appointment x : appointmentList) {
                String query = " insert into appointments (id, description, date, starttime, endtime, state, createdby)" + " values (?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, x.getRandomId());
                preparedStmt.setString(2, x.getDescription());
                preparedStmt.setObject(3, x.getAppoinmentDate());
                preparedStmt.setTime(4, x.getStarttime());
                preparedStmt.setTime(5, x.getEndtime());
                preparedStmt.setBoolean(6, x.isState());
                preparedStmt.setString(7, x.getCreatedby());

                preparedStmt.execute();
            }
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}

