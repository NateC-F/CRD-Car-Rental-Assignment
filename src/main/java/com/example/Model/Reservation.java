package com.example.Model;

import java.sql.Date;

public class Reservation
{
    private Car car;
    private Customer customer;
    private int numberOfDaysRenting;
    private Date startOfReserve;
    private Date endOfReserve;
    private Date dateReturned;
    private int invoiceNumber;
    private boolean isLate;
}
