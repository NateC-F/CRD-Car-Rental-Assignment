package com.example;

import com.example.DAO.CarDAO;
import com.example.Model.Car;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main
{
    public static void main(String[] args) throws SQLException
    {

        HashMap<String, Car> listOfCars = new HashMap<>();
        ArrayList<Car> cars = new CarDAO().loadAllCars();

        for (Car car:cars)
        {
            listOfCars.put(car.getLicensePlate(),car);
        }



        System.out.println("Welcome To CRD Car Reservation System What Would You Like To Do?\n" +
                "1) Create A Reservation\n" +
                "2) Return A Reservation\n" +
                "3) Lookup A Reservation\n" +
                "4) Look At The Car Lot\n" +
                "5) Quit\n");


        for (Car car:listOfCars.values())
        {
            String carListString = "";
            if (car.isInUse())
                carListString = car.toString() + " : Car Is Current Unavailable";
            else carListString = car.toString() + " : Car Is Available";

            System.out.println(carListString);
        }

    }
}