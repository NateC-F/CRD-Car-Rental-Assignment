package com.example.Model;

import org.junit.jupiter.api.Test;

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


}
