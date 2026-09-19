package com.example.DAO;

import com.example.Model.Customer;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerDAOTest
{
    //DAO Create functions were tested separately

    @Test
    void testGetCustomerIdFromLicense() throws SQLException
    {
        CustomerDAO customerDAO = new CustomerDAO();

        int customerId = customerDAO.getCustomerIdFromLicense("TEST001");

        assertTrue(customerId == 7);
    }

    @Test
    void testGetCustomerFromDatabaseId() throws SQLException
    {
        CustomerDAO customerDAO = new CustomerDAO();

        int customerId = customerDAO.getCustomerIdFromLicense("TEST001");

        Customer customer = customerDAO.getCustomerFromDatabaseId(customerId);

        assertNotNull(customer);
        assertEquals("Test Customer", customer.getCustomerName());
        assertEquals("TEST001", customer.getCustomerLicense());
    }

    @Test
    void testGetCustomerFromInvalidDatabaseId() throws SQLException
    {
        CustomerDAO customerDAO = new CustomerDAO();

        Customer customer = customerDAO.getCustomerFromDatabaseId(-1);

        assertNull(customer);
    }

    @Test
    void testLookUpCustomerByLicense() throws SQLException
    {
        CustomerDAO customerDAO = new CustomerDAO();

        Customer customer = customerDAO.lookUpCustomerByLicense("TEST001");

        assertNotNull(customer);
        assertEquals("Test Customer", customer.getCustomerName());
        assertEquals("TEST001", customer.getCustomerLicense());
    }

    @Test
    void testLookUpCustomerByInvalidLicense() throws SQLException
    {
        CustomerDAO customerDAO = new CustomerDAO();

        Customer customer = customerDAO.lookUpCustomerByLicense("DOESNOTEXIST");

        assertNull(customer);
    }
}
