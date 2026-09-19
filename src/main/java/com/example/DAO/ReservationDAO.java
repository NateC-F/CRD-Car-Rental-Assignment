package com.example.DAO;

import com.example.Model.*;

import java.sql.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.random.RandomGenerator;

public class ReservationDAO
{
    private PreparedStatement statement;
    private String sqlQuery;


    public Reservation lookUpReservation(ReservationSearchType searchType, String lookUpField)
    {
        switch (searchType)
        {
            case INVOICE:
                sqlQuery = "Select * From reservations where invoice_number = ?";
                break;
            case CAR_LICENSE_PLATE:
                sqlQuery = "Select r.* From reservations r JOIN cars c ON r.car_id = c.car_id WHERE c.license_plate = ?";
                break;
            case DRIVER_LICENSE:
                sqlQuery = "Select r.* From reservations r JOIN customers c ON r.customer_id = c.customer_id Where c.customer_license = ?";
                break;
        }

        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1,lookUpField);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
            {
                int invoiceNumber = resultSet.getInt("invoice_number");
                Date reservationStart = resultSet.getDate("reservation_start");
                Date reservationEnd = resultSet.getDate("reservation_end");
                Date dateReturned = resultSet.getDate("actual_return_date");
                Boolean isLate = resultSet.getBoolean("is_late");
                Car car = ListOfCars.getInstance().searchCarById(resultSet.getInt("car_id"));
                Customer customer = new CustomerDAO().getCustomerFromDatabaseId(resultSet.getInt("customer_id"));
                Double amountPaid = resultSet.getDouble("amount_paid");

                Reservation reservation = new Reservation(car,customer,reservationStart,reservationEnd,dateReturned,invoiceNumber,isLate,amountPaid);

                return reservation;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return null;
    }


    public int createReservation(int carID,int customerID, Date startDay, Date endDay)
    {
        sqlQuery = "INSERT INTO reservations" +
                "(customer_id, car_id, reservation_start, reservation_end)" +
                "VALUES (?,?,?,?)";

        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlQuery,PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1,customerID);
            statement.setInt(2,carID);
            statement.setDate(3,startDay);
            statement.setDate(4,endDay);

            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys())
            {
                if (generatedKeys.next())
                {
                    return generatedKeys.getInt(1);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return -1;
    }

    public boolean isCarReserved(Car car, Date startDate, Date endDate)
    {
        String sqlQuery =
                "SELECT COUNT(*) FROM reservations " +
                        "WHERE car_id = ? " +
                        "AND reservation_start < ? " +
                        "AND reservation_end > ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery))
        {
            statement.setInt(1, car.getDatabaseID());
            statement.setDate(2, endDate);
            statement.setDate(3, startDate);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getInt(1) > 0;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return false;
    }

    public boolean customerHasActiveReservation(int customerId)
    {
        String sqlQuery =
                "SELECT COUNT(*) FROM reservations " +
                        "WHERE customer_id = ? " +
                        "AND actual_return_date IS NULL";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery))
        {
            statement.setInt(1, customerId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getInt(1) > 0;
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return false;
    }


    public boolean carHasBeenReturned(Date dateReturned, double amountPaid, int invoiceNumber)
    {
        sqlQuery = "UPDATE reservations SET actual_return_date = ?, amount_paid = ? WHERE invoice_number = ?";

        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlQuery);
            statement.setDate(1,dateReturned);
            statement.setDouble(2,amountPaid);
            statement.setInt(3,invoiceNumber);

            statement.executeUpdate();
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return false;
    }


}
