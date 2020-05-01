package com.company;

public class Main {

    public static void main(String[] args) {
	MyCalendar calendar1 = new MyCalendar("09:00", "20");
	MyCalendar calendar2 = new MyCalendar("10:00", "18:30");

	calendar1.addMeeting("9", "10:30");
	calendar1.addMeeting("12:00", "13:00");
	calendar1.addMeeting("16:00", "18:30");

	calendar2.addMeeting("10", "11:30");
	calendar2.addMeeting("12:30", "14:30");
	calendar2.addMeeting("14:30", "15:00");
	calendar2.addMeeting("16:00", "17");

	System.out.println(MeetingPlanner.showAvailableMeetingTimes(calendar1, calendar2, "00:30"));
    }
}
