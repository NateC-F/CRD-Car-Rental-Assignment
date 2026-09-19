package com.example.Model;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;


public class CarTest
{

    //Car car = new Car(10000,"2013 Honda Civic", "ABC123", 25.00, "SEDAN", "GAS", false,1);

    @Test
    void testReserveCar()
    {
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false,1);

        car.reserveCar();

        assertTrue(car.isInUse());
    }

   @Test
   void testReturnCar()
   {
       Car car = new Car(10000,"2013 Honda Civic", "ABC123",
           25.00, "SEDAN", "GAS", true,1);

       car.returnCar(23809);

       assertEquals(23809, car.getMilesOnCar());
       assertFalse(car.isInUse());
   }

   @Test
    void testRefillCarAbove95Percent()
   {
       Car car = new Car(10000,"2013 Honda Civic", "ABC123",
               25.00, "SEDAN", "GAS", true,1);

       assertFalse(car.doesCarNeedToBeRefilled(24.5));
   }
    @Test
    void testRefillCarBelow95Percent()
    {
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", true,1);

        assertTrue(car.doesCarNeedToBeRefilled(10));
    }

    @Test
    void testExtraMilesAboveLimit()
    {
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", true,1);

        assertTrue(car.hasExtraMiles(10600, 500));
    }

    @Test
    void testExtraMilesBelowLimit()
    {
        Car car = new Car(10000,"2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", true,1);

        assertFalse(car.hasExtraMiles(10600, 1000));
    }


    @Test
    void testingToStringLateCar()
    {
        Car car = new Car(10000, "2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", false, 1);

        Customer customer = new Customer("John Doe", "S123456789");

        Reservation reservation = new Reservation(
                car,
                customer,
                Date.valueOf("2026-09-10"),
                Date.valueOf("2026-09-17"),
                null,
                123,
                true,
                null
        );

        String result = reservation.toString();

        assertTrue(result.contains("123"));
        assertTrue(result.contains("2013 Honda Civic"));
        assertTrue(result.contains("ABC123"));
    }

    @Test
    void testRefillCarExactly95Percent()
    {
        Car car = new Car(10000, "2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", true, 1);

        // 95% of 25 = 23.75
        assertFalse(car.doesCarNeedToBeRefilled(23.75));
    }

    @Test
    void testExtraMilesExactlyAtLimit()
    {
        Car car = new Car(10000, "2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", true, 1);

        assertFalse(car.hasExtraMiles(10500, 500));
    }

    @Test
    void testReturnCarWithSameMileage()
    {
        Car car = new Car(10000, "2013 Honda Civic", "ABC123",
                25.00, "SEDAN", "GAS", true, 1);

        car.returnCar(10000);

        assertEquals(10000, car.getMilesOnCar());
        assertFalse(car.isInUse());
    }


    @Test
    void testHybridRefillBelowHalfTank()
    {
        Car car = new Car(10000, "2023 Toyota RAV4", "ABC123",
                14.5, "SUV", "HYBRID", false, 1);

        double cost = car.getFuelTypeBehavior().calculateReFuelCost(12.0, 14.5);
        // 2.5 gallons needed
        // 2.5 * $2.00 = $5.00
        assertEquals(5.00, cost, 0.01);
    }

    @Test
    void testHybridRefillCrossesHalfTank()
    {
        Car car = new Car(10000, "2023 Toyota Prius", "ABC123",
                11.3, "SEDAN", "HYBRID", false, 1);

        double cost = car.getFuelTypeBehavior().calculateReFuelCost(4.0, 11.3);
        // Fuel needed = 7.3
        // First 5.65 at $2.00 = $11.30
        // Remaining 1.65 at $3.60 = $5.94
        // Total = $17.24
        assertEquals(17.24, cost, 0.01);
    }

}
