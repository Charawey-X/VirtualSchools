package org.example.interfaces;

import org.example.models.Attendance;

import java.util.List;

public interface IAttendance {
    Attendance getAttendance(int userid, String date);
    boolean addAttendance(Attendance attendance);
    List<Attendance> getAllAttendanceByDate(String date);
}
