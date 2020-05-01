package com.company;

import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MeetingPlanner {

    //method that finds possible meeting times for two calendars
    public static String showAvailableMeetingTimes(MyCalendar firstCalendar, MyCalendar secondCalendar, String meetingTime) {
        StringBuilder availableMeetingTimes = new StringBuilder();

        List<MyMeeting> firstCalendarAvailable = getAvailableTime(firstCalendar);
        List<MyMeeting> secondCalendarAvailable = getAvailableTime(secondCalendar);

        //if working hours aren't set properly, this message will be returned. Calendar is null because getAvailableTime will return null
        //if meetings list doesn't exist. It won't exist unless work hours are set properly
        if (firstCalendarAvailable == null || secondCalendarAvailable == null) {
            availableMeetingTimes.append("No meetings are available, at least one of the calendars has work hours unset");
            return availableMeetingTimes.toString();
        }

        //case there is no free time in one of the calendars
        if (firstCalendarAvailable.isEmpty() || secondCalendarAvailable.isEmpty()) {
            availableMeetingTimes.append("There is no possibility of setting a meeting. At least one of the calendars is full");
            return availableMeetingTimes.toString();
        }

        //counters are used to iterate over free spaces in calendars
        int counterFirst = 0;
        int counterSecond = 0;
        //there is at least one, the check was made above
        //meetingTime is changed to minutes
        int meetingTimeMins = TimeResolver.hoursToMinutes(meetingTime);

        //quits when reaches end of list of free spaces in one of calendars
        while (counterFirst < firstCalendarAvailable.size() && counterSecond < secondCalendarAvailable.size()) {
            //at the beginning of each loop free calendar spaces references are created. Won't be out of bounds due to upper condition.
            MyMeeting secondCalSpace = secondCalendarAvailable.get(counterSecond);
            MyMeeting firstCalSpace = firstCalendarAvailable.get(counterFirst);
            //iterating over calendars free spaces until there is a space big enough to fit meetingTime in.
            while (counterFirst < firstCalendarAvailable.size() && firstCalSpace.getDuration() < meetingTimeMins) {
                firstCalSpace = firstCalendarAvailable.get(counterFirst++);
            }
            //if there are no more spaces big enough to fit the meeting, the loop will break
            if (counterFirst >= firstCalendarAvailable.size()) {
                break;
            }
            while (counterSecond < secondCalendarAvailable.size() && secondCalSpace.getDuration() < meetingTimeMins) {
                secondCalSpace = secondCalendarAvailable.get(counterSecond++);
            }
            if (counterSecond >= secondCalendarAvailable.size()) {
                break;
            }

            //getting top of range of time when meeting can occur (meeting time: 00:30, free time: 11:40-12:50. 11:40 + 01:10 - 00:30 = 12:20(everything is in minutes))
            int firstCalMeetingTop = firstCalSpace.getStartTime() + firstCalSpace.getDuration() - meetingTimeMins;
            int secondCalMeetingTop = secondCalSpace.getStartTime() + secondCalSpace.getDuration() - meetingTimeMins;

            //checking if free spaces are in the same range, if not- taking next from the list
            if (secondCalMeetingTop < firstCalSpace.getStartTime()) {
                ++counterSecond;
            }
            else if (firstCalMeetingTop < secondCalSpace.getStartTime()){
                ++counterFirst;
            }
            else if (secondCalSpace.getStartTime() <= firstCalMeetingTop){
                //calculating common time range
                int startTime = Math.max(firstCalSpace.getStartTime(), secondCalSpace.getStartTime());
                int endTime = Math.min(firstCalMeetingTop, secondCalMeetingTop);

                //if availableMeetingTimes has "[" at pos 0 it means there is at leas one time range- ", " needs to be added before the next one
                if (availableMeetingTimes.indexOf("[") == 0) {
                    availableMeetingTimes.append(", ");
                }
                //appending calculated time range, changing minutes to hours
                availableMeetingTimes.append("[");
                availableMeetingTimes.append(TimeResolver.minutesToHours(startTime));
                availableMeetingTimes.append(", ");
                availableMeetingTimes.append(TimeResolver.minutesToHours(endTime));
                availableMeetingTimes.append("]");

                //incrementing counter of space with lower top value, if top values are equal, both are incremented
                if (firstCalMeetingTop < secondCalMeetingTop) {
                    ++counterFirst;
                }
                else if (secondCalMeetingTop < firstCalMeetingTop) {
                    ++counterSecond;
                }
                else {
                    ++counterSecond;
                    ++counterFirst;
                }
            }
        }
        //case meeting time was too long, no possible meeting time was found
        if (availableMeetingTimes.toString().length() == 0) {
            return availableMeetingTimes.append(("There is no possibility of setting a meeting. Shorter meeting  might be possible")).toString();
        }
        return availableMeetingTimes.toString();
    }

    //private method that creates a list of MyMeeting objects that are free spaces in calendar
    private static List<MyMeeting> getAvailableTime(MyCalendar calendar) {
        //case meetings list doesn't exist in particular calendar
        if (calendar.getMeetings() == null) {
            System.out.println("meeting list doesn't exist");
            return null;
        }
        List<MyMeeting> freeTimeList = new LinkedList<>();

        //case no meetings are planned. Whole day is added as free space
        if (calendar.getMeetings().isEmpty()) {
            freeTimeList.add(new MyMeeting(calendar.getWorkStartTime(), calendar.getWorkEndTime() - calendar.getWorkStartTime()));
            return freeTimeList;
        }

        //case there are planned meetings. Free time will be added as MyMeeting objects to freeTimeList
        //empty spaces between the meetings are calculated and added
        int availableTime = calendar.getWorkStartTime();
        MyMeeting currentMeeting;
        for (int i=0; i<calendar.getMeetings().size(); i++) {
            currentMeeting = calendar.getMeetings().get(i);
            if (availableTime != currentMeeting.getStartTime()) {
                MyMeeting freeTime = new MyMeeting(availableTime, currentMeeting.getStartTime() - availableTime);
                freeTimeList.add(freeTime);
            }
            availableTime = currentMeeting.getStartTime() + currentMeeting.getDuration();
        }

        //case there is free time between last meeting and end of working hours
        if (availableTime != calendar.getWorkEndTime()) {
            currentMeeting = new MyMeeting(availableTime, calendar.getWorkEndTime() - availableTime);
            freeTimeList.add(currentMeeting);
        }

        return freeTimeList;
    }
}
