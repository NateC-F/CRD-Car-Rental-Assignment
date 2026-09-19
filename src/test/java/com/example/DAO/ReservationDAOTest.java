package com.example.DAO;

import com.example.Model.Car;
import com.example.Model.ListOfCars;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationDAOTest
{
    //DAO Create functions were tested separately

    @Test
    void testCarIsReservedDuringOverlappingDates() throws SQLException
    {
        ReservationDAO reservationDAO = new ReservationDAO();
        CarDAO carDAO = new CarDAO();
        carDAO.loadAllCars();

        Car car = ListOfCars.getInstance().searchCarById(1);

        boolean isReserved = reservationDAO.isCarReserved(car,
                Date.valueOf("2026-09-21"), Date.valueOf("2026-09-23"));

        assertTrue(isReserved);
    }

    @Test
    void testCarIsReservedDuringOverlappingDates2() throws SQLException
    {
        ReservationDAO reservationDAO = new ReservationDAO();
        CarDAO carDAO = new CarDAO();
        carDAO.loadAllCars();

        Car car = ListOfCars.getInstance().searchCarById(1);

        boolean isReserved = reservationDAO.isCarReserved(car,
                Date.valueOf("2026-09-20"), Date.valueOf("2026-09-23"));

        assertTrue(isReserved);
    }

    @Test
    void testCarIsReservedOnPreviousReservationEndDate() throws SQLException
    {
        ReservationDAO reservationDAO = new ReservationDAO();
        CarDAO carDAO = new CarDAO();
        carDAO.loadAllCars();

        Car car = ListOfCars.getInstance().searchCarById(1);

        boolean isReserved = reservationDAO.isCarReserved(car,
                Date.valueOf("2026-09-21"), Date.valueOf("2026-09-23"));

        assertTrue(isReserved);
    }

    @Test
    void testCarIsNotReservedForFreeDates() throws SQLException
    {
        ReservationDAO reservationDAO = new ReservationDAO();
        CarDAO carDAO = new CarDAO();
        carDAO.loadAllCars();

        Car car = ListOfCars.getInstance().searchCarById(1);

        boolean isReserved = reservationDAO.isCarReserved(car,
                Date.valueOf("2026-09-23"), Date.valueOf("2026-09-25"));

        assertFalse(isReserved);
    }

    @Test
    void testCustomerHasActiveReservation() throws SQLException
    {
        ReservationDAO reservationDAO = new ReservationDAO();

        boolean hasActiveReservation = reservationDAO.customerHasActiveReservation(1);

        assertTrue(hasActiveReservation);
    }

    @Test
    void testCustomerHasActiveReservationNoCustomer() throws SQLException
    {
        ReservationDAO reservationDAO = new ReservationDAO();

        boolean hasActiveReservation =
                reservationDAO.customerHasActiveReservation(-1);

        assertFalse(hasActiveReservation);
    }

    @Test
    void testCustomerHasNoActiveReservation() throws SQLException
    {
        ReservationDAO reservationDAO = new ReservationDAO();
        //customer 7 was inserted into database prior and still failed so this check passed
        boolean hasActiveReservation = reservationDAO.customerHasActiveReservation(7);

        assertFalse(hasActiveReservation);
    }

    @Test
    void testReturnedReservationIsNotActive() throws SQLException
    {
        ReservationDAO reservationDAO = new ReservationDAO();

        // Customer with a reservation that has already been returned
        int customerId = 8;

        assertFalse(reservationDAO.customerHasActiveReservation(customerId));
    }


}
