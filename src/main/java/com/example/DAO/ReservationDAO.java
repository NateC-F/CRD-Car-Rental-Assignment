package com.example.DAO;

import com.example.Model.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

                Reservation reservation = new Reservation(car,customer,reservationStart,reservationEnd,dateReturned,invoiceNumber,isLate);

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


}
