package grouppbo2;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import static grouppbo2.appointment.readAppointmentArray;
import static grouppbo2.appointment.inputAppointmentDatabase;
import static grouppbo2.user.inputUserDatabase;
import static grouppbo2.user.readUser;


public class mainMenu {
    public static void main(String[] args) {
        ArrayList<user> userList = new ArrayList<>();
        ArrayList<appointment> appointmentList = new ArrayList<>();

        Scanner scInt = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);

        int currentIDCount = 1;
        int currentAIDCount = 0;
        int loggedID = -1;


        // Initialize Admin
        userList.add(new user(0, "admin", "admin", "admin", UUID.randomUUID().toString()));

        userList = readUser();
        appointmentList = readAppointmentArray();

        ///////////////Main Program Start/////////////
        do {
            // Input Login
            String inputUser, inputPass;
            System.out.print("Enter username: ");
            inputUser = sc.nextLine();
            System.out.print("Enter password: ");
            inputPass = sc.nextLine();

            // Check Login
            boolean validLogin = false;
            for (user x : userList) {
                if (x.getUsername().equals(inputUser) && x.getPassword().equals(inputPass)) {
                    loggedID = x.getId();
                    validLogin = true;
                    break;
                }
            }

            ////////login output///////////
            if (!validLogin) {
                System.out.println(("Username/Password Salah"));
            } else if (userList.get(loggedID).getRole().equals("admin")) {
                ////////////////// ADMIN MENU START//////////////
                System.out.println("Welcome! " + inputUser);
                do {
                    ////input command/////
                    System.out.print("Enter Your Command : ");
                    String strCommand = sc.nextLine();
                    String[] aryCommand = strCommand.split(" ");
                    boolean commandBerhasil = false;

                    if (aryCommand[0].equals("help")) {
                        System.out.println("[admin] reg user [username] [password]");
                        System.out.println("\tRegister user with username and password\n");
                        System.out.println("[admin] change userpassword [username] [newpassword]");
                        System.out.println("\tChange password from other users\n");
                        System.out.println("[admin] change role [username] [new role]");
                        System.out.println("\tChange user role: ADMIN or REGULAR\n");
                        System.out.println("[admin/reguler] change password [oldpassword] [newpassword]");
                        System.out.println("\tChange Password\n");
                        commandBerhasil = true;
                    }

                    // menambahkan user baru dengan default role reguler - reg user [username] [password]
                    if(aryCommand[0].equals("reg") && aryCommand[1].equals("user") && aryCommand.length == 4){
                        userList.add(new user(currentIDCount,aryCommand[2],aryCommand[3],"regular",UUID.randomUUID().toString()));
                        currentIDCount++;
                        commandBerhasil = true;
                        inputUserDatabase(userList);
                        System.out.println("Registrasi User Successfull");
                    }

                    // update password user lain - change userpassword [user] [newpassword]
                    if(aryCommand[0].equals("change") && aryCommand[1].equals("userpassword") && aryCommand.length == 4){
                        int userIndex = -1;

                        // Find User
                        for(user x : userList){
                            if(x.getUsername().equals(aryCommand[2])){
                                userIndex = x.getId();
                                break;
                            }
                        }

                        // Output
                        if(userIndex == -1){
                            System.out.println("User tidak ditemukan!");
                        }
                        else{
                            userList.get(userIndex).setPassword(aryCommand[3]);
                            System.out.println("Update password successfully.");
                        }
                        commandBerhasil = true;
                        inputUserDatabase(userList);
                    }

                    // Change Role - change role [user] [newrole]
                    if(aryCommand[0].equals("change") && aryCommand[1].equals("role") && aryCommand.length == 4){
                        int userIndex = -1;

                        // Find User
                        for(user x : userList){
                            if(x.getUsername().equals(aryCommand[2])){
                                userIndex = x.getId();
                                break;
                            }
                        }

                        // Output
                        if(userIndex == -1){
                            System.out.println("User tidak ditemukan!");
                        }
                        else{
                            userList.get(userIndex).setRole(aryCommand[3]);
                            System.out.println("Update role successfully.");
                        }
                        commandBerhasil = true;
                        inputUserDatabase(userList);
                    }

                    // mengupdate password di akun sendiri - change password [oldpassword] [newpassword]
                    if(aryCommand[0].equals("change") && aryCommand[1].equals("password") && aryCommand.length == 4){
                        if(userList.get(loggedID).getPassword().equals(aryCommand[2])){
                            userList.get(loggedID).setPassword(aryCommand[3]);
                            System.out.println("Update password successfully.");
                        }
                        else{
                            System.out.println("Password lama salah!");
                        }
                        commandBerhasil = true;
                        inputUserDatabase(userList);
                    }

                    //command logout
                    if (aryCommand[0].equals("logout")) {
                        break;
                    }

                    // Command Tidak Dikenali
                    if(!commandBerhasil){
                        System.out.println("Command tidak dikenali!");
                    }

                }while (true);
                ///////END MENU INTERFACE ADMIN/////////
            } else {
                ////////////////// REGULER MENU START//////////////
                System.out.println("Welcome! " + inputUser);
                do {
                    ////input command/////
                    System.out.print("Enter Your Command : ");
                    String strCommand = sc.nextLine();
                    String[] aryCommand = strCommand.split(" ");
                    boolean commandBerhasil = false;

                    if (aryCommand.length == 1) {
                        if(aryCommand[0].equals("help")){
                            System.out.println("[admin] reg user [username] [password]");
                            System.out.println("\tRegister user with username and password\n");
                            System.out.println("[admin] change userpassword [username] [newpassword]");
                            System.out.println("\tChange password from other users\n");
                            System.out.println("[admin] change role [username] [new role]");
                            System.out.println("\tChange user role: ADMIN or REGULAR\n");
                            System.out.println("change password [oldpassword] [newpassword]");
                            System.out.println("\tChange Password\n");
                            System.out.println("new [description]");
                            System.out.println("\tCreate new appointment\n");
                            System.out.println("cancel [no.app]");
                            System.out.println("\tCancel appointment\n");
                            System.out.println("peek -a");
                            System.out.println("\tSee all upcoming appointments\n");
                            System.out.println("peek -ad");
                            System.out.println("\tSee today upcoming appointments\n");
                            System.out.println("see [no.app]");
                            System.out.println("\tSee an appointment\n");
                            System.out.println("export");
                            System.out.println("\tDownload appointments in a file\n");
                            commandBerhasil = true;
                        }
                    } else {
                        // mengupdate password di akun sendiri - change password [oldpassword] [newpassword]
                        if(aryCommand[0].equals("change") && aryCommand[1].equals("password") && aryCommand.length == 4){
                            if(userList.get(loggedID).getPassword().equals(aryCommand[2])){
                                userList.get(loggedID).setPassword(aryCommand[3]);
                                System.out.println("Update password successfully.");
                            }
                            else{
                                System.out.println("Password lama salah!");
                            }
                            commandBerhasil = true;
                            inputUserDatabase(userList);
                        }

                        if (aryCommand[0].equals("new") && aryCommand.length > 1) {

                            // mendapatkan data description dari inputan
                            String description = ""; //untuk menampung nilai description
                            for(int i=1;i<aryCommand.length;i++){
                                if(i > 1){description += " ";}
                                description += aryCommand[i];
                            }

                            // mendapatkan data tanggal dan waktu
                            System.out.print("Enter date (format: dd-mm-yyyy): ");
                            String appoinmentDate = sc.nextLine();
                            System.out.print("Enter start time (format: hh:mm): ");
                            String starttimeStr = sc.nextLine();
                            System.out.print("Enter end time (format: hh:mm): ");
                            String endtimeStr = sc.nextLine();

                            try {
                                // menconvert data yang dari menilai string menjadi format yang sesuai di database sql
                                Date thisAppoinmentDate = new SimpleDateFormat("dd-MM-yyyy").parse(appoinmentDate);
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                Time starttime = new Time(sdf.parse(starttimeStr).getTime());
                                Time endtime = new Time(sdf.parse(endtimeStr).getTime());

                                // menginput data lalu di simpan di array list lalu di insert menggunakan method inputAppointmentDatabase
                                appointmentList.add(new appointment(currentAIDCount,description,thisAppoinmentDate, starttime, endtime, false, userList.get(loggedID).getUsername(),UUID.randomUUID().toString()));
                                currentAIDCount++;
                                System.out.println("Add Appointment Successfull!");
                            } catch (ParseException e) {
                                System.out.println("Error Exception: "+e);
                            }
                            commandBerhasil = true;
                            inputAppointmentDatabase(appointmentList);
                        }

                        // melihat semua daftar agenda - peek -a
                        if(aryCommand[0].equals("peek") && aryCommand[1].equals("-a")){
                            for(appointment x : appointmentList){
                                System.out.println(x.getRandomId().substring(0,6)+" - "+ x.getDescription());
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                SimpleDateFormat dftime = new SimpleDateFormat("HH:mm");
                                System.out.println(df.format(x.getAppoinmentDate())+" "+dftime.format(x.getStarttime())+" - "+dftime.format(x.getEndtime()));
                                if(x.isState() == false){
                                    System.out.println("Status: ON");
                                }
                                else{
                                    System.out.println("Status: CANCELLED");
                                }
                                System.out.println();
                            }
                            commandBerhasil = true;
                        }

                        // melihat daftar agenda hari ini - peek -ad
                        if(aryCommand[0].equals("peek") && aryCommand[1].equals("-ad")){
                            for(appointment x : appointmentList){
                                Date currentDate = new Date();
                                SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
                                if(df.format(currentDate).equals(df.format(x.getAppoinmentDate()))){ // akan mencari tanggal yang sama dengan hari ini
                                    System.out.println(x.getRandomId().substring(0,6)+" - "+ x.getDescription()); //akan menampikan random id
                                    SimpleDateFormat dfd = new SimpleDateFormat("dd-MM-yyyy");
                                    SimpleDateFormat dftime = new SimpleDateFormat("HH:mm");
                                    System.out.println(dfd.format(x.getAppoinmentDate())+" "+dftime.format(x.getStarttime())+" - "+dftime.format(x.getEndtime()));
                                    if(x.isState() == false){
                                        System.out.println("Status: ON");
                                    }
                                    else{
                                        System.out.println("Status: CANCELLED");
                                    }
                                    System.out.println();
                                }
                            }
                            commandBerhasil = true;
                        }

                        // menampilkan agenda berdasarkan id - see [id app]
                        if(aryCommand[0].equals("see") && aryCommand.length == 2){
                            for(appointment x : appointmentList){
                                if(x.getRandomId().substring(0,6).equals(aryCommand[1])){
                                    System.out.println(x.getRandomId().substring(0,6)+" - "+ x.getDescription());
                                    SimpleDateFormat dfd = new SimpleDateFormat("dd-MM-yyyy");
                                    SimpleDateFormat dftime = new SimpleDateFormat("hh:mm");
                                    System.out.println(dfd.format(x.getAppoinmentDate())+" "+dftime.format(x.getStarttime())+" - "+dftime.format(x.getEndtime()));
                                    if(x.isState() == false){
                                        System.out.println("Status: ON");
                                    }
                                    else{
                                        System.out.println("Status: CANCELLED");
                                    }
                                    System.out.println();
                                }
                            }
                            commandBerhasil = true;
                        }

                        // mambatalkan agenda - cancel [id app]
                        if(aryCommand[0].equals("cancel") && aryCommand.length == 2){
                            for(appointment x : appointmentList){
                                if(x.getRandomId().substring(0,6).equals(aryCommand[1])){
                                    if(x.isState() == true){
                                        System.out.println("Appointment already CANCELLED.");
                                    }
                                    else{
                                        x.setState(true);
                                    }
                                }
                            }
                            commandBerhasil = true;
                            inputAppointmentDatabase(appointmentList);
                        }

                        //command logout
                        if (aryCommand[0].equals("logout")) {
                            break;
                        }

                        // Command Tidak Dikenali
                        if(!commandBerhasil){
                            System.out.println("Command tidak dikenali!");
                        }

                    }

                }while (true);
            }
            ////////////////// REGULER MENU END//////////////

        }while (true);
        ///////////////Main Program End/////////////
    }
}