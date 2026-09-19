package com.example.DAO;

import com.example.Model.Customer;

import java.sql.*;

public class CustomerDAO
{
    private PreparedStatement statement;
    private String sqlQuery;

    public Customer lookUpCustomerByLicense(String license)
    {
        sqlQuery = "SELECT * FROM customers WHERE customer_license = ?";

        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1,license);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                String name = resultSet.getString("customer_name");
                return new Customer(name,license);
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public int getCustomerIdFromLicense(String license)
    {
        sqlQuery = "SELECT * FROM customers WHERE customer_license = ?";

        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1,license);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return resultSet.getInt("customer_id");
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }
        return -1;
    }

    public Customer getCustomerFromDatabaseId(int id)
    {
        sqlQuery = "SELECT * FROM customers WHERE customer_id = ?";

        try(Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlQuery);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                return new Customer(resultSet.getString("customer_name"),resultSet.getString("customer_license"));
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }


    public void createCustomerRecord(String name, String license)
    {
        sqlQuery = "Insert Into customers (customer_name,customer_license) " +
                "VALUES (?,?)";

        try (Connection connection = JDBC.getConnection())
        {
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1,name);
            statement.setString(2,license);
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }



}
