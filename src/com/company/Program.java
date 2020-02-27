package com.company;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {

    public void run() {
        uppgift_1();
        uppgift_2();
        uppgift_3();
        uppgift_3_1();
        uppgift_4();
        uppgift_5();
    }

    private void uppgift_1() {
        System.out.println("\n-----------task 1 -----------------------------------------------------------------\n");
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.of(1984, 8, 02);
        long days = ChronoUnit.DAYS.between(birthday, today);
        System.out.println("Days from my birthday " + days);
    }

    private void uppgift_2() {
        //Date 2020/04/20 T = 07:30 in New York
        System.out.println("\n-----------task 2 ---------------------------------------------------------------\n");
        LocalDateTime inNewYork = LocalDateTime.of(2020, Month.APRIL, 20, 07, 30);
        ZonedDateTime timeInNewyork = ZonedDateTime.of(inNewYork, ZoneId.of("America/New_York"));
        //System.out.println("Time in NewYork: " + timeInNewyork.toLocalDateTime());
        ZonedDateTime sameTimeInMalmö = timeInNewyork.toInstant().atZone(ZoneId.of("Europe/Stockholm"));
        //System.out.println("Same time in Stockholm " + sameTimeInMalmö.toLocalDateTime());

        DateTimeFormatter formatterForUS = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        String newYorkTime = timeInNewyork.format(formatterForUS);
        System.out.println("Time in NewYork: " + newYorkTime);


        DateTimeFormatter formatterForSweden = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        String swedenTime = sameTimeInMalmö.format(formatterForSweden);
        System.out.println("Same time in Sweden: " + swedenTime);

    }

    private void uppgift_3() {
        System.out.println("\n---------task 3 ------------------------------------------------------------------\n");
        int totalCount = 0;
        for (int year = 1900; year <= Year.now().getValue(); year++) {
            int count = 0;
            int month = 0;
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = formatter.parse(year + "-01-13");
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                while (month++ < 12) {
                    if (Calendar.FRIDAY == cal.get(Calendar.DAY_OF_WEEK))
                        count++;
                    cal.add(Calendar.MONTH, 1);
                }
                System.out.println("Number of friday(s) on 13th: " + count + " in year: " + year);
                totalCount += count;

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total fridays from 1900: " + totalCount);

    }

    private void uppgift_3_1() {
        System.out.println("\n----------task-3 (another way)-------------------------------------------------------------------\n");

        LocalDate startDate = LocalDate.of(1900, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.now();
        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        List<LocalDate> days = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDays)
                .filter(date -> (date.getDayOfWeek() == DayOfWeek.FRIDAY) && (date.getDayOfMonth() == 13))
                .collect(Collectors.toList());
        int fridays = days.size();
        //System.out.println(days.size());
        double totalYears = ChronoUnit.YEARS.between(startDate, endDate);
        double totalDays = ChronoUnit.DAYS.between(startDate, endDate);
        double average = fridays / totalYears;
        double average2 = totalDays / fridays;
        System.out.println("average fridays per year :--- " + average);
        System.out.println("average fridays from 1900:--- " + average2);


    }


    private void uppgift_4() {
        System.out.println("\n--------- task 4 ------------------------------------------------------------\n");
        LocalDate startDate = LocalDate.of(1984, 8, 02);
        LocalDate endDate = LocalDate.of(2008, 12, 31);
        long yearsTill2008 = ChronoUnit.YEARS.between(startDate, endDate);
        //System.out.println(yearsTill2008);
        double hoursTill2008 = 0;

        for (int year = 0; year <= yearsTill2008; year++) {
            double x = (14.5 / 60) * 365.24;
            hoursTill2008 += x;

        }
        int daysBefore = (int) (hoursTill2008 / 24);
        System.out.println("Days spent b4 mobile came: " + daysBefore);


        double hoursFrom2008 = 0;

        for (long age = yearsTill2008; age <= 80; age++) {
            double y = (27.0 / 60) * 365.24;
            hoursFrom2008 += y;
        }
        int daysAfter = (int) (hoursFrom2008 / 24);
        System.out.println("Days spent after mobile came till 80 years age: " + daysAfter);

        int days = (daysBefore + daysAfter);
        System.out.println("Total days spent: " + days);

    }

    private void uppgift_5() {
        System.out.println("\n-------task 5------------------------------------------------------------------------------");
        double sleepingHr = 8;
        double schoolJobHr = 8 + 1;
        double toiletHr = 0.02 * 24;
        double otherActivities = (30 / 60) + (60 / 60) + (30 / 60);

        double totalHoursSpentInWeekdays = sleepingHr + schoolJobHr + toiletHr + otherActivities;
        double totalHoursSpentInWeekEnds = totalHoursSpentInWeekdays - schoolJobHr;

        LocalDate today = LocalDate.now();
        LocalDate at65Years = today.plusYears(65);
        LocalDate at80Years = today.plusYears(80);

        long daysTill65 = ChronoUnit.DAYS.between(today, at65Years);
        long daysFrom65To80 = ChronoUnit.DAYS.between(at65Years, at80Years);
        long totalDaysFromBirthTo80 = ChronoUnit.DAYS.between(today, at80Years);

        List<LocalDate> weekEndsList = Stream.iterate(today, date -> date.plusDays(1))
                .limit(daysTill65)
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
                .collect(Collectors.toList());
        double weekEnds = weekEndsList.size();  // weekends before 65
        double weekDays = daysTill65 - weekEnds; //weekdays before 65


        double hoursSpentFromBirthTill65 = ((weekDays * totalHoursSpentInWeekdays) + (weekEnds * totalHoursSpentInWeekEnds));

        double hoursSpentFrom65To80 = (daysFrom65To80 * (sleepingHr + 2 * (toiletHr + otherActivities)));

        double totalHoursSpent = hoursSpentFromBirthTill65 + hoursSpentFrom65To80;
        double totalDaysSpent = totalHoursSpent / 24;
        double daysRemaining = totalDaysFromBirthTo80 - totalDaysSpent;

        double inPercentage = (100 * daysRemaining) / totalDaysFromBirthTo80;

        System.out.printf("\n%.2f %% of our total life is free time\n", inPercentage);

        double hoursRemaining = daysRemaining * 24;

        System.out.printf("\n%.2f hours of our total life are free hours\n" , hoursRemaining);

        // if we consider age range between 18 - 65  of mobile user who spend average 3h /day,
        //people uses mobile for 47 years of their life span.
        double totalMobileHours = (65 - 18)*3;
        double freeTimeAfterMobileHours = hoursRemaining - totalMobileHours;

        System.out.printf("\nFree hours of our life after mobile hours: %.2f  \n", freeTimeAfterMobileHours);
    }


}

