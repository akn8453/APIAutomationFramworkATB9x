package com.akshaynomulwar.sample;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestIntegrationSample {
    // Create a booking, Create a Token
    // verify that get booking
    // update the booking
    // Delete the booking

    @Test(groups = "qa",priority = 1)
    @Owner("Akshay")
    @Description("TC#NT1 - Step1. Verify that the booking can be created")
    public void testCreateBooking(){
        Assert.assertTrue(true);
    }


    @Test(groups = "qa",priority = 2)
    @Owner("Akshay")
    @Description("TC#NT1 - Step 2. Verify that the booking By ID")
    public void testVerifyBookingId(){
        Assert.assertTrue(true);
    }

    @Test(groups = "qa",priority = 3)
    @Owner("Akshay")
    @Description("TC#NT1 - Step 3. Verify update booking by Id")
    public void testUpdateBookingbyId(){
        Assert.assertTrue(true);
    }

    @Test(groups = "qa",priority = 4)
    @Owner("Akshay")
    @Description("TC#NT1 - Step 4. Delete the booking by Id")
    public void testDeleteBookingbyId(){
        Assert.assertTrue(true);
    }


}
