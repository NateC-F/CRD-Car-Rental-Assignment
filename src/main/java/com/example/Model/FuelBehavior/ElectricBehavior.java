package com.example.Model.FuelBehavior;

public class ElectricBehavior implements FuelTypeBehavior
{
    private final double COST_PER_UNIT_OF_FUEL = 2.00;

    //================================================================================

    @Override
    public double calculateReFuelCost(double currentFuelLevel, double maxFuelCapacity)
    {
        double fuelNeeded = maxFuelCapacity - currentFuelLevel;
        return fuelNeeded*COST_PER_UNIT_OF_FUEL;
    }

}
