package com.example.Model.FuelBehavior;

public class DieselBehavior implements FuelTypeBehavior
{
    private final double COST_PER_UNIT_OF_FUEL = 4.25;

    //================================================================================

    @Override
    public double calculateReFuelCost(double currentFuelLevel, double maxFuelCapacity)
    {
        double fuelNeeded = maxFuelCapacity - currentFuelLevel;
        return fuelNeeded*COST_PER_UNIT_OF_FUEL;
    }
}
