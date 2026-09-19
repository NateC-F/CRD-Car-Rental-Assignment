package com.example;

import com.example.DAO.CarDAO;
import com.example.DAO.CustomerDAO;
import com.example.DAO.ReservationDAO;
import com.example.Model.*;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws SQLException
    {
        CarDAO carDAO = new CarDAO();
        carDAO.loadAllCars();

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running)
        {
            System.out.println(
                    "\nWelcome To CRD Car Reservation System\n" +
                            "What Would You Like To Do?\n\n" +
                            "1) Create A Reservation\n" +
                            "2) Return A Reservation\n" +
                            "3) Lookup A Reservation\n" +
                            "4) Look At The Car Lot\n" +
                            "5) Quit\n"
            );

            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1":
                    createReservation(scanner);
                    break;

                case "2":
                    returnReservation(scanner);
                    break;

                case "3":
                    lookupReservation(scanner);
                    break;

                case "4":
                    lookAtCarLot();
                    break;

                case "5":
                    running = false;
                    System.out.println("Thank you for using CRD Car Reservation System!");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number from 1-5.");
            }
        }

        scanner.close();
    }


    private static void createReservation(Scanner scanner)
    {
        System.out.println("========================");
        System.out.println(" Creating A Reservation");
        System.out.println("========================");
        CustomerDAO customerDAO = new CustomerDAO();
        ReservationDAO reservationDAO = new ReservationDAO();


        // ============================================================
        // 1. Find/Create Customer
        // ============================================================

        System.out.println("Please enter the customers license");
        String license = scanner.nextLine();

        Customer customer = customerDAO.lookUpCustomerByLicense(license);

        if (customer != null)
            System.out.println(customer.getCustomerName() + " has been pulled");
        else
        {
            System.out.println("No records of this customer has been found please enter their name");
            String name = scanner.nextLine();
            customer = new Customer(name,license);
            customer.saveCustomerToDatabase();
        }

        // ============================================================
        // 2. Check If Customer Already Has An Active Reservation
        // ============================================================

        int customerID = customerDAO.getCustomerIdFromLicense( customer.getCustomerLicense() );
        if (reservationDAO.customerHasActiveReservation(customerID))
        {
            System.out.println( "Customer has an active reservation. " + "Customers are only allowed one reservation at a time." );
            return;
        }

        // ============================================================
        // 3. Select Vehicle Type
        // ============================================================

        System.out.println("What Type Of Vehicle Does The Customer Want?\n1) Sedan\n2) Suv\n3) Van");

        int carTypeChoice = scanner.nextInt();
        scanner.nextLine();
        CarType selectedCarType;

        switch (carTypeChoice)
        {
            case 1:
                selectedCarType = CarType.SEDAN;
                break;
            case 2:
                selectedCarType = CarType.SUV;
                break;
            case 3:
                selectedCarType = CarType.VAN;
                break;
            default:
                System.out.println("Invalid vehicle type."); return;
        }

        // ============================================================
        // 4. Select Reservation Dates
        // ============================================================

        System.out.println();
        System.out.println("Please enter the reservation start date (YYYY-MM-DD):");

        String startDateInput = scanner.nextLine();

        System.out.println("Please enter the reservation end date (YYYY-MM-DD):");

        String endDateInput = scanner.nextLine();
        Date startDate;
        Date endDate;

        try
        {
            startDate = Date.valueOf(startDateInput);
            endDate = Date.valueOf(endDateInput);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println(
                    "Invalid date format. Please use YYYY-MM-DD."
            );

            return;
        }

        if (startDate.after(endDate))
        {
            System.out.println(
                    "The reservation start date cannot be after the end date."
            );

            return;
        }

        // ============================================================
        // 5. Show Cars Of Selected Type
        // ============================================================

        System.out.println();
        System.out.println("Available " + selectedCarType + "s:");

        boolean carsAvailable = false;

        for (Car car : ListOfCars.getInstance().getListOfCarsById().values())
        {
            if (car.getCarType() == selectedCarType && !car.isInUse())
            {
                if (!reservationDAO.isCarReserved(car, startDate, endDate))
                {
                    System.out.println(car);
                    carsAvailable = true;
                }
            }
        }

        if (!carsAvailable)
        {
            System.out.println("There are no " + selectedCarType + "s available for those dates.");
            return;
        }

        // ============================================================
        // 6. Select Specific Car
        // ============================================================

        System.out.println("Please enter the license plate of the car you would like:");

        String carLicensePlate = scanner.nextLine();

        Car car = ListOfCars.getInstance().searchCarByLicensePlate(carLicensePlate);

        if (car == null)
        {
            System.out.println("No car was found with that license plate.");
            return;
        }

        if (car.getCarType() != selectedCarType)
        {
            System.out.println("That car does not match the vehicle type you selected.");
            return;
        }

        // ============================================================
        // 7. Double Check Car Availability
        // ============================================================

        if (reservationDAO.isCarReserved(car, startDate, endDate))
        {
            System.out.println("That car is no longer available for those dates.");
            return;
        }

        // ============================================================
        // 8. Create Reservation
        // ============================================================

        int invoiceNumber = reservationDAO.createReservation(car.getDatabaseID(), customerID, startDate, endDate);

        if (invoiceNumber != -1)
        {
            Date today = new Date(System.currentTimeMillis());

            if (startDate.equals(today))
                car.reserveCar();

            System.out.println("========================");
            System.out.println(" Reservation Created!");
            System.out.println("========================");
            System.out.println("Invoice Number: " + invoiceNumber);
            System.out.println("Customer: " + customer.getCustomerName());
            System.out.println("Car: " + car.getCarModel());
            System.out.println("License Plate: "+car.getLicensePlate());
            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
        }
        else
            System.out.println("There was an error creating the reservation.");

    }

    private static void returnReservation(Scanner scanner)
    {
        System.out.println("========================");
        System.out.println("      Return A Car      ");
        System.out.println("========================");

        ReservationDAO reservationDAO = new ReservationDAO();

        // ============================================================
        // 1. Look Up Reservation
        // ============================================================

        System.out.println("Please enter the invoice number of the reservation:");

        while (!scanner.hasNextInt())
        {
            System.out.println("Enter numbers only please");
            scanner.next();
        }

        int invoiceNumber = scanner.nextInt();
        scanner.nextLine();

        Reservation reservation = reservationDAO.lookUpReservation(ReservationSearchType.INVOICE, String.valueOf(invoiceNumber));

        if (reservation == null)
        {
            System.out.println("There is no reservation with this invoice.");
            return;
        }

        System.out.println();
        System.out.println(reservation.toString());

        // ============================================================
        // 2. Check If Reservation Has Already Been Returned
        // ============================================================

        if (reservation.getDateReturned() != null)
        {
            System.out.println("This reservation has already been returned.");
            return;
        }

        // ============================================================
        // 3. Get Current Mileage
        // ============================================================

        System.out.println();
        System.out.println("Please enter the car's current mileage:");

        while (!scanner.hasNextInt())
        {
            System.out.println("Enter numbers only please");
            scanner.next();
        }

        int newMiles = scanner.nextInt();
        scanner.nextLine();

        if (newMiles < reservation.getCar().getMilesOnCar())
        {
            System.out.println("The new mileage cannot be less than the car's current mileage.");
            return;
        }

        // ============================================================
        // 4. Get Current Fuel Level
        // ============================================================

        System.out.println();
        System.out.println("Please enter the car's current fuel level:");

        while (!scanner.hasNextDouble())
        {
            System.out.println("Enter numbers only please");
            scanner.next();
        }

        double currentFuelLevel = scanner.nextDouble();
        scanner.nextLine();

        if (currentFuelLevel < 0 || currentFuelLevel > reservation.getCar().getMaxFuelCapacity())
        {
            System.out.println("Invalid fuel level.");
            return;
        }

        // ============================================================
        // 5. Calculate Total
        // ============================================================
        Date returnDate = new Date(System.currentTimeMillis());
        reservation.setDateReturned(returnDate);
        boolean isLate = returnDate.after(reservation.getEndOfReserve());
        reservation.setLate(isLate);

        double total = reservation.calculateTotal(newMiles, currentFuelLevel);

        System.out.println();
        System.out.println("========================");
        System.out.println("       Amount Due");
        System.out.println("========================");
        System.out.printf("Amount Due: $%.2f%n", total);

        // ============================================================
        // 6. Select Payment Method
        // ============================================================

        System.out.println();
        System.out.println("How will the customer pay?");
        System.out.println("1) Cash");
        System.out.println("2) Card");

        int paymentChoice;

        while (!scanner.hasNextInt())
        {
            System.out.println("Please enter 1 for Cash or 2 for Card.");
            scanner.next();
        }

        paymentChoice = scanner.nextInt();
        scanner.nextLine();

        String paymentMethod;

        switch (paymentChoice)
        {
            case 1:
                paymentMethod = "Cash";
                break;

            case 2:
                paymentMethod = "Card";
                break;

            default:
                System.out.println("Invalid payment method.");
                return;
        }

        // ============================================================
        // 7. Confirm Payment
        // ============================================================

        System.out.printf("Confirm payment of $%.2f by %s? (Y/N): ", total, paymentMethod);

        String paymentConfirmation = scanner.nextLine();

        if (!paymentConfirmation.equalsIgnoreCase("Y"))
        {
            System.out.println("Payment cancelled. The car has not been returned.");
            return;
        }

        // ============================================================
        // 8. Update Database
        // ============================================================

        reservation.getCar().returnCar(newMiles);

        boolean reservationUpdated = reservationDAO.carHasBeenReturned(returnDate, total, invoiceNumber);

        if (!reservationUpdated)
        {
            System.out.println("The car was updated, but there was an error updating the reservation.");
            return;
        }

        // ============================================================
        // 9. Display Receipt
        // ============================================================

        System.out.println();
        System.out.println("========================");
        System.out.println("       Car Returned");
        System.out.println("========================");
        System.out.println("Invoice Number: " + invoiceNumber);
        System.out.println("Car: " + reservation.getCar().getCarModel());
        System.out.println("License Plate: " + reservation.getCar().getLicensePlate());
        System.out.println("Return Date: " + returnDate);
        System.out.printf("Amount Paid: $%.2f%n", total);
        System.out.println("Payment Method: " + paymentMethod);
    }


    private static void lookupReservation(Scanner scanner)
    {
        System.out.println("========================");
        System.out.println("  Look Up a Reservation ");
        System.out.println("========================");
        System.out.println("Please enter the invoice number of the reservation");


        while(!scanner.hasNextInt())
        {
            System.out.println("Enter numbers only please");
            scanner.next();
        }
        Integer invoiceNumber = scanner.nextInt();
        scanner.nextLine();

        Reservation reservation = new ReservationDAO().lookUpReservation(ReservationSearchType.INVOICE,String.valueOf(invoiceNumber.intValue()));

        if (reservation != null)
            System.out.println(reservation.toString());
        else System.out.println("There is no reservation with this invoice");

    }

    private static void lookAtCarLot()
    {
        for (Car car: ListOfCars.getInstance().getListOfCarsById().values())
        {
            System.out.println(car.toString());
        }

    }


}