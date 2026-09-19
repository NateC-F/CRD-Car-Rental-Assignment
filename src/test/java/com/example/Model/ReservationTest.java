package com.example.Model;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import static org.junit.jupiter.api.Assertions.*;


public class ReservationTest
{

    //Car car = new Car(10000,"2013 Honda Civic", "ABC123", 25.00, "SEDAN", "GAS", false);
    //Customer customer = new Customer("John Doe", "S123456789");

    @Test
    public void testingToStringLateCar()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),null,123,true);

        System.out.println(reservation.toString());
    }
    @Test
    public void testingToStringCar()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-20"),null,123,false);

        System.out.println(reservation.toString());
    }
    @Test
    public void testingToStringOnTimeCar()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-17"),123,false);

        System.out.println(reservation.toString());
    }

    @Test
    public void testingToStringReturnedLate()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false);
        Customer customer = new Customer("John Doe", "S123456789");
        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-20"),123,false);

        System.out.println(reservation.toString());
    }


    @Test
    public void testingCalculateTotalNormalReturn()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-17"),123,false);


        double totalCost = reservation.calculateTotal(10500,25);
        //it should be 350 = 50*5
        assertEquals(350,totalCost);
    }

    @Test
    public void testingCalculateTotalLateReturn()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-17"),123,true);


        double totalCost = reservation.calculateTotal(10500,25);
        //it should be 1050 = 7*150
        assertEquals(1050,totalCost);
    }

    @Test
    public void testingCalculateTotalExcessiveMiles()
    {
        System.out.println();
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-17"),123,false);


        double totalCost = reservation.calculateTotal(11000,25);
        //it should be 402.5 = 7 * 50 * 1.15
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
                25.00, "SEDAN", "GAS", false);
        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(car, customer,  Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),Date.valueOf("2026-09-17"),123,false);


        double totalCost = reservation.calculateTotal(11000,20);
        //it should be 402.5 = ((7 * 50) + (5*3.60))  * 1.15
        assertEquals(423.2,totalCost);
    }

}
