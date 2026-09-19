package com.example.Model;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import static org.junit.jupiter.api.Assertions.*;


public class ReservationTest
{

    //Car car = new Car(10000,"2013 Honda Civic", "ABC123", 25.00, "SEDAN", "GAS", false,1);
    //Customer customer = new Customer("John Doe", "S123456789");

    @Test
    public void testingToStringLateCar()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false,1);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),null,123,true,null);

        System.out.println(reservation.toString());
    }
    @Test
    public void testingToStringCar()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false,1);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-20"),null,123,false,null);

        System.out.println(reservation.toString());
    }
    @Test
    public void testingToStringOnTimeCar()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false,1);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-17"),123,false,null);

        System.out.println(reservation.toString());
    }

    @Test
    public void testingToStringReturnedLate()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false,1);
        Customer customer = new Customer("John Doe", "S123456789");
        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-20"),123,false,null);

        System.out.println(reservation.toString());
    }


    @Test
    public void testingCalculateTotalNormalReturn()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false,1);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-17"),123,false,null);


        double totalCost = reservation.calculateTotal(10500,25);
        //it should be 350 = 50*5
        assertEquals(350,totalCost);
    }

    @Test
    public void testingCalculateTotalLateReturn()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false,1);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-17"),123,true,null);


        double totalCost = reservation.calculateTotal(10500,25);
        //it should be 1050 = 7*150
        assertEquals(1050,totalCost);
    }

    @Test
    public void testingCalculateTotalExcessiveMiles()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false,1);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-17"),123,false,null);


        double totalCost = reservation.calculateTotal(11000,25);
        //it should be 402.5 = 7 * 50 * 1.15
        totalCost = Math.round(totalCost * 100.0) / 100.0;
        assertEquals(402.5,totalCost);

        //says its off Expected :402.5
        //Actual   :402.49999999999994
        //Rounding difference
    }

    @Test
    public void testingCalculateTotalExcessiveMilesAndNeedsGas()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false,1);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-17"),123,false,null);


        double totalCost = reservation.calculateTotal(11000,20);
        //it should be 402.5 = ((7 * 50) + (5*3.60))  * 1.15
        assertEquals(423.2,totalCost);
    }

    @Test
    void testCalculateTotalEarlyReturn()
    {
        Car car = new Car(10000, "2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false, 1);

        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer, Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"), Date.valueOf("2026-09-15"), 123, false, null);

        double totalCost = reservation.calculateTotal(10500, 25);
        // Reservation was for 7 days, even though the car was returned early.
        // 7 * $50 = $350
        assertEquals(350.00, totalCost, 0.01);
    }

    @Test
    void testCalculateTotalExactlyAtMileageLimit()
    {
        Car car = new Car(10000, "2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false, 1);

        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer, Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"), Date.valueOf("2026-09-17"), 123, false, null);

        // 7 days * 100 miles = exactly 700 allowed miles
        double totalCost = reservation.calculateTotal(10700, 25);
        assertEquals(350.00, totalCost, 0.01);
    }

    @Test
    void testCalculateTotalOneMileOverMileageLimit()
    {
        Car car = new Car(10000, "2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false, 1);

        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer, Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"), Date.valueOf("2026-09-17"), 123, false, null);

        // 701 miles used when only 700 are allowed
        double totalCost = reservation.calculateTotal(10701, 25);
        // 350 * 1.15 = 402.50
        assertEquals(402.50, totalCost, 0.01);
    }
}
