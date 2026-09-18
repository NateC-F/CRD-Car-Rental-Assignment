package com.example.Model;

import com.example.DAO.CarDAO;

import java.util.ArrayList;
import java.util.HashMap;

public class ListOfCars {

    private static final ListOfCars INSTANCE = new ListOfCars();

    private final HashMap<Integer, Car> listOfCarsById = new HashMap<>();
    private final HashMap<String, Car> listOfCarsByLicensePlate = new HashMap<>();


    private ListOfCars() {};

    public static ListOfCars getInstance()
    {
        return INSTANCE;
    }

    public void addCar(Car car, int idInDatabase)
    {
        listOfCarsById.put(idInDatabase, car);
        listOfCarsByLicensePlate.put(car.getLicensePlate(),car);
    }

    public Car searchCarById(int id)
    {
        return listOfCarsById.get(id);
    }

    public Car searchCarByLicensePlate(String license)
    {
        return listOfCarsByLicensePlate.get(license);
    }

    public HashMap<Integer,Car> getListOfCarsById()
    {
        return listOfCarsById;
    }

    public HashMap<String,Car> getListOfCarsByLicensePlate()
    {
        return listOfCarsByLicensePlate;
    }
}

