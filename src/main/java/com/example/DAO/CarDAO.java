package com.example.DAO;

import com.example.Model.Car;
import com.example.Model.ListOfCars;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarDAO
{
    private PreparedStatement statement;
    private String sqlQuery;



    public void loadAllCars() throws SQLException
    {
        sqlQuery = "Select * From cars";
        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next())
            {
                int milesOnCar = resultSet.getInt("car_miles");
                String carModel = resultSet.getString("car_model");
                String carLicensePlate = resultSet.getString("car_license_plate");
                double carMaxFuelCapacity = resultSet.getDouble("car_tank_capacity");
                String carType = resultSet.getString("car_type");
                String fuelType = resultSet.getString("car_fuel_type");
                boolean carInUse = resultSet.getBoolean("car_in_use");

                ListOfCars.getInstance().addCar(
                        (new Car(milesOnCar,carModel,carLicensePlate,carMaxFuelCapacity,carType,fuelType,carInUse)),
                        resultSet.getInt("car_id"));
            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    public void saveChangeOnCar(Car carToUpdate)
    {
        sqlQuery = "UPDATE cars SET car_miles = ?, car_in_use = ? WHERE car_license_plate = ?";

        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(3, carToUpdate.getLicensePlate());
            statement.setBoolean(2,carToUpdate.isInUse());
            statement.setInt(1, carToUpdate.getMilesOnCar());

            statement.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }


}
