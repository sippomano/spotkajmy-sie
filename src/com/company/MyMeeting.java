package com.company;

public class MyMeeting implements Comparable<MyMeeting> {

    //both variables are in minutes
    private int startTime;
    private int duration;

    public MyMeeting(int startTime, int duration) {
        this.startTime = startTime;
        this.duration = duration;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int compareTo(MyMeeting o) {
        return Integer.compare(this.startTime, o.getStartTime());
    }

}
