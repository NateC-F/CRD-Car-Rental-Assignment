package com.example.Model;

import com.example.Model.FuelBehavior.*;

public class Car
{
    private int milesOnCar;
    private String licensePlate;
    private double maxFuelCapacity;
    private CarType carType;
    private FuelType fuelType;
    private FuelTypeBehavior fuelTypeBehavior;
    private final double CAR_RETURN_FUEL_PERCENTAGE_THRESHOLD = 0.95;

    //================================================================================


    public Car (int milesOnCar, String licensePlate, double maxFuelCapacity, String carType, String fuelType)
    {
        this.milesOnCar = milesOnCar;
        this.licensePlate = licensePlate;
        this.maxFuelCapacity = maxFuelCapacity;
        this.carType = CarType.valueOf(carType);
        this.fuelType = FuelType.valueOf(fuelType);
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

    public boolean doesCarNeedToBeRefilled(double currentTankLevel)
    {
        return !(currentTankLevel / maxFuelCapacity > CAR_RETURN_FUEL_PERCENTAGE_THRESHOLD);
    }

    public boolean hasExtraMiles(int newMiles, int allowedMiles)
    {
        return (newMiles- milesOnCar) > allowedMiles;
    }



}
