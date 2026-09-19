package com.example.Model;

import com.example.DAO.CarDAO;
import com.example.Model.FuelBehavior.*;

public class Car
{
    private int milesOnCar;
    private String carModel;
    private String licensePlate;
    private double maxFuelCapacity;
    private CarType carType;
    private FuelType fuelType;
    private FuelTypeBehavior fuelTypeBehavior;
    private final double CAR_RETURN_FUEL_PERCENTAGE_THRESHOLD = .95;
    private boolean inUse;

    //================================================================================


    public Car (int milesOnCar, String carModel, String licensePlate, double maxFuelCapacity, String carType, String fuelType, boolean inUse)
    {
        this.milesOnCar = milesOnCar;
        this.carModel = carModel;
        this.licensePlate = licensePlate;
        this.maxFuelCapacity = maxFuelCapacity;
        this.carType = CarType.valueOf(carType);
        this.fuelType = FuelType.valueOf(fuelType);
        this.inUse = inUse;
        switch (this.fuelType)
        {
            case GAS:
                this.fuelTypeBehavior = new GasBehavior();
                break;
            case DIESEL:
                this.fuelTypeBehavior = new DieselBehavior();
                break;
            case HYBRID:
                this.fuelTypeBehavior = new HybridBehavior();
                break;
            case ELECTRIC:
                this.fuelTypeBehavior = new ElectricBehavior();
                break;
        }
    }



    public void reserveCar()
    {
        inUse = true;
        new CarDAO().saveChangeOnCar(this);
    }

    public void returnCar(int newMiles)
    {
        milesOnCar = newMiles;
        inUse = false;
        new CarDAO().saveChangeOnCar(this);
    }

    public boolean doesCarNeedToBeRefilled(double currentTankLevel)
    {
        return !(currentTankLevel / maxFuelCapacity > CAR_RETURN_FUEL_PERCENTAGE_THRESHOLD);
    }

    public boolean hasExtraMiles(int newMiles, int allowedMiles)
    {
        return (newMiles- milesOnCar) > allowedMiles;
    }


    public double getMaxFuelCapacity()
    {
        return maxFuelCapacity;
    }
    public FuelTypeBehavior getFuelTypeBehavior()
    {
        return fuelTypeBehavior;
    }
    public String getLicensePlate()
    {
        return  licensePlate;
    }

    public String toString()
    {
        String available="";
        if (!isInUse())
            available = " this vehicle is currently available";
        else available = "this vehicle is currently unavailable";
        return carModel + "("+carType+")"+", fuel tank of: " + maxFuelCapacity+" units," + " license plate: " + licensePlate + available;
    }

    public boolean isInUse()
    {
        return inUse;
    }

    public int getMilesOnCar()
    {
        return milesOnCar;
    }

    public String getCarModel()
    {
        return carModel;
    }

    public CarType getCarType()
    {
        return carType;
    }
}
