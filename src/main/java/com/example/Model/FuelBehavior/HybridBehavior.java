package com.example.Model.FuelBehavior;

public class HybridBehavior implements FuelTypeBehavior
{
    private final double COST_PER_FIRST_HALF_OF_UNIT_OF_FUEL = 2.00;
    private final double COST_PER_SECOND_HALF_OF_UNIT_OF_FUEL = 3.60;

    //================================================================================

    @Override
    public double calculateReFuelCost(double currentFuelLevel, double maxFuelCapacity)
    {
        double halfTank = maxFuelCapacity/2;
        double totalCost = 0.0;

        if (maxFuelCapacity - currentFuelLevel < halfTank)
        {
            totalCost += halfTank * COST_PER_FIRST_HALF_OF_UNIT_OF_FUEL;
            totalCost += (halfTank - currentFuelLevel) * COST_PER_SECOND_HALF_OF_UNIT_OF_FUEL;
        }
        else totalCost += halfTank * COST_PER_FIRST_HALF_OF_UNIT_OF_FUEL;

        return totalCost;
    }
}
