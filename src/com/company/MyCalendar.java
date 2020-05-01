package com.company;

import java.util.LinkedList;
import java.util.List;

public class MyCalendar {

    private int workStartTime;
    private int workEndTime;
    private List<MyMeeting> meetings;

    public MyCalendar(String workStartTime, String workEndTime) {
        //passed hours are changed to minutes
        this.workStartTime = TimeResolver.hoursToMinutes(workStartTime);
        this.workEndTime = TimeResolver.hoursToMinutes(workEndTime);
        //list of meetings will be created only if working hours are set correctly. Otherwise it'll be created when these are corrected with setters
        if (this.workStartTime != -1 && this.workEndTime != -1) {
            //list of meetings is created. LinkedList because there won't be random access, only iterating
            meetings = new LinkedList<>();
        }
        else {
            meetings = null;
        }
    }


    public void addMeeting(String startHour, String endHour) {
        //meeting won't be added if working hours aren't set correctly
        if (workStartTime == -1 || workEndTime == -1) {
            System.out.println("Invalid working hours, valid format is hours only(7, 9, 14) or hours:minutes(07:43, 11:12, 18:07)");
            return;
        }
        //changing passed time to minutes
        int startHourMinutes = TimeResolver.hoursToMinutes(startHour);
        int endHourMinutes = TimeResolver.hoursToMinutes(endHour);

        //check if passed hours were in proper format
        if (startHourMinutes == -1 || endHourMinutes == -1) {
            System.out.println("Invalid start/end hours, valid format is hours only(7, 9, 14) or hours:minutes(07:43, 11:12, 18:07)");
            return;
        }

        //check if passed hours were within working hours
        if (startHourMinutes < workStartTime || startHourMinutes > workEndTime || endHourMinutes < workStartTime || endHourMinutes > workEndTime) {
            System.out.println("Meeting is outside of range of work hours");
            return;
        }

        //adding meeting after all the checks
        meetings.add(new MyMeeting(startHourMinutes, endHourMinutes - startHourMinutes));
        System.out.println("Meeting added successfully");
        //sorting meetings after each insertion
        //program lets meetings to happen simultaneously as there can be several meetings happening at once
        meetings.sort(MyMeeting::compareTo);
    }

    public int getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(String workStartTime)
    {
        this.workStartTime = TimeResolver.hoursToMinutes(workStartTime);
        //if meetings list doesn't exist and both work hours are set correctly, it'll be created and meetings can be added.
        if (this.workStartTime != -1 && this.workEndTime != -1 && meetings == null) {
            meetings = new LinkedList<>();
        }
    }

    public List<MyMeeting> getMeetings() {
        return meetings;
    }

    public int getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(String workEndTime)
    {
        this.workEndTime = TimeResolver.hoursToMinutes(workEndTime);
        //if meetings list doesn't exist and both work hours are set correctly, it'll be created and meetings can be added.
        if (this.workStartTime != -1 && this.workEndTime != -1 && meetings == null) {
            meetings = new LinkedList<>();
        }
    }
}
