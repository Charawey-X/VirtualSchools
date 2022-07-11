package org.example.dao;

import org.example.interfaces.IAttendance;
import org.example.models.Attendance;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class AttendanceDao implements IAttendance {
    private Connection connection;

    public AttendanceDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * @param userid 
     * @param date
     * @return
     */
    @Override
    public Attendance getAttendance(int userid, String date) {
        try {
            return connection.createQuery("SELECT * FROM attendance WHERE userid = :userid AND date = :date")
                    .addParameter("userid", userid)
                    .addParameter("date", date)
                    .executeAndFetchFirst(Attendance.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting attendance");
        }
    }

    /**
     * @param attendance 
     * @return
     */
    @Override
    public boolean addAttendance(Attendance attendance) {
        try {
            return connection.createQuery("INSERT INTO attendance (userid, date, activity) " +
                    "VALUES (:userid, :date, :activity)")
                    .bind(attendance)
                    .executeUpdate().getResult() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param date 
     * @return
     */
    @Override
    public List<Attendance> getAllAttendanceByDate(String date) {
        try {
            return connection.createQuery("SELECT * FROM attendance WHERE date = :date")
                    .addParameter("date", date)
                    .executeAndFetch(Attendance.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting all attendance");
        }
    }
}
