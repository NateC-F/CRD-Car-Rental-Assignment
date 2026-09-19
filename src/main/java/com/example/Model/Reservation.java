package com.example.Model;

import com.example.DAO.ReservationDAO;

import java.sql.Date;
import java.time.temporal.ChronoUnit;

public class Reservation
{
    private Car car;
    private Customer customer;
    private int numberOfDaysRenting;
    private Date startOfReserve;
    private Date endOfReserve;
    private Date dateReturned;
    private Integer invoiceNumber;
    private boolean isLate;
    private double amountPaid;
    private final int COST_PER_DAY_NOT_LATE = 50;
    private final int COST_PER_DAY_LATE = 150;
    private final int MILLAGE_ALLOWED_PER_DAY = 100;
    private final double EXCESSIVE_MILLAGE_FLAT_FEE = 1.15;

    public Reservation(Car car, Customer customer, Date startOfReserve,
                       Date endOfReserve, Date dateReturned, Integer invoiceNumber, boolean isLate)
    {
        this.car = car;
        this.customer = customer;
        this.startOfReserve = startOfReserve;
        this.endOfReserve = endOfReserve;
        numberOfDaysRenting =(int) ChronoUnit.DAYS.between(startOfReserve.toLocalDate(), endOfReserve.toLocalDate());
        this.dateReturned = dateReturned;
        this.invoiceNumber = invoiceNumber;
        this.isLate = isLate;
    }


    @Override
    public String toString()
    {
        Date currentDate = new Date(System.currentTimeMillis());

        String lateString = getLateString(currentDate);

        String toString = "Invoice Number: " +invoiceNumber + "\nIs A "+ car.getCarModel()+
                "\nAnd Is Being Rented By: " + customer.getCustomerName()+
                "\nAnd Is Being Rented From " + startOfReserve +" to " + endOfReserve+
                "\nCurrently the car " +lateString;

        return toString;
    }

    private String getLateString(Date currentDate)
    {
        String lateString;

        // Reservation has not started yet
        if (currentDate.before(startOfReserve))
            lateString = "reservation has not started yet";

        // Car is still being rented
        if (dateReturned == null)
        {
            if (currentDate.after(endOfReserve))
                lateString = "is still actively being used but is late";
            else
                lateString = "is still actively being used but is not late";
        }

        // Car has been returned
        else
        {
            if (dateReturned.after(endOfReserve))
                lateString = "has been returned late";
            else
                lateString = "has been returned on time";
        }
        return lateString;
    }


    public double calculateTotal(int newMiles, int currentFuelLevel)
    {
        double fuelCost=0;
        double excessiveMileFee=1;
        double daysUsedCost=0;
        int milesAllowed = MILLAGE_ALLOWED_PER_DAY * numberOfDaysRenting;


        if (car.doesCarNeedToBeRefilled(currentFuelLevel))
            fuelCost = car.getFuelTypeBehavior().calculateReFuelCost(currentFuelLevel,car.getMaxFuelCapacity());

        if (car.hasExtraMiles(newMiles,milesAllowed))
            excessiveMileFee = EXCESSIVE_MILLAGE_FLAT_FEE;

        int totalDaysUsed = numberOfDaysRenting;
        int daysLate = (int) ChronoUnit.DAYS.between(endOfReserve.toLocalDate(), dateReturned.toLocalDate());

        if (isLate)
        {
            totalDaysUsed += daysLate;

            daysUsedCost = totalDaysUsed * COST_PER_DAY_LATE;
        }
        else daysUsedCost = totalDaysUsed * COST_PER_DAY_NOT_LATE;

        amountPaid = (fuelCost + daysUsedCost) * excessiveMileFee;
        amountPaid = Math.round(amountPaid * 100.0) / 100.0;

        return amountPaid;
    }


    public boolean validateReservationDates(Car car, Date startDate, Date endDate)
    {
        if (startDate == null || endDate == null)
        {
            return false;
        }

        if (startDate.after(endDate))
        {
            return false;
        }

        if (new ReservationDAO().isCarReserved(car, startDate, endDate))
        {
            return false;
        }

        return true;
    }




    public void setInvoiceNumber(int invoiceNumber)
    {
        this.invoiceNumber = invoiceNumber;
    }

    public Car getCar()
    {
        return car;
    }

    public Date getStartOfReserve()
    {
        return startOfReserve;
    }

    public Date getEndOfReserve()
    {
        return endOfReserve;
    }

    public Date getDateReturned()
    {
        return dateReturned;
    }

    public int getInvoiceNumber()
    {
        return invoiceNumber;
    }
}
