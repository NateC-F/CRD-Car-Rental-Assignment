package com.example;

import com.example.DAO.CarDAO;
import com.example.Model.Car;
import com.example.Model.ListOfCars;

import java.sql.SQLException;
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
    }

    private static void returnReservation(Scanner scanner)
    {
        System.out.println("========================");
        System.out.println("      Return A Car      ");
        System.out.println("========================");
    }

    private static void lookupReservation(Scanner scanner)
    {
        System.out.println("========================");
        System.out.println("  Look Up a Reservation ");
        System.out.println("========================");
    }

    private static void lookAtCarLot()
    {
        for (Car car: ListOfCars.getInstance().getListOfCarsById().values())
        {
            System.out.println(car.toString());
        }

    }


}