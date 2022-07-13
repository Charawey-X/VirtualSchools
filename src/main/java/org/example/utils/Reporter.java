package org.example.utils;

import org.example.dao.AttendanceDao;
import org.example.dao.UserDao;
import org.example.interfaces.IAttendance;
import org.example.models.Attendance;
import org.example.models.Users;
import org.sql2o.Connection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

public class Reporter extends TimerTask {
    private Connection connection;

    public Reporter(Connection connection) {
        this.connection = connection;
    }

    /**
     *
     */
    @Override
    public void run() {

        try{
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            //Format the previous day
            Date date = new Date();
            date.setTime(date.getTime() - 86400000);

            String formattedDate = formatter.format(date);
            // Get all attendances
            IAttendance attendance = new AttendanceDao(connection);
            List<Attendance> allAttendances = new ArrayList<>();

            allAttendances = attendance.getAllAttendanceByDate(
                    date.toString()
            );

            //Get unique attendees by userid
            List<Users> uniqueAttendees = new ArrayList<>();
            for (Attendance attendance1 : allAttendances) {
                if (!uniqueAttendees.contains(attendance1.getUserid())) {

                    //Get user by id
                    Users user = new UserDao(connection).getUser(attendance1.getUserid());
                    uniqueAttendees.add(user);
                }
            }
            // Send Mail to Each Attendee
            for (Users user : uniqueAttendees) {
                String message = "Hi " + user.getName() + ",\n\n" +
                        "You have " + allAttendances.size() + " attendances on " + formattedDate + ".\n\n" +
                        "Regards,\n" +
                        "Attendance System";
                Mailer.sendMail(user.getEmail(), "Attendance Report", message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
