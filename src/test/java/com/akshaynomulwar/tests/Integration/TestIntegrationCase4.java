package com.akshaynomulwar.tests.Integration;

import com.akshaynomulwar.base.BaseTest;
import static org.assertj.core.api.Assertions.assertThat;

import com.akshaynomulwar.endpoints.APIConstants;
import com.akshaynomulwar.pojos.Booking;
import com.akshaynomulwar.pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.assertj.core.api.AssertionsForClassTypes;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationCase4 extends BaseTest {


    //Create Booking
    @Test(groups = {"Integration","P0"},priority = 1)
    @Owner("Akshay")
    @Description("TC#NT1 - Step1. Verify that the Booking can be created")
    public void testCreateBooking(ITestContext iTestContext){
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString()).post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "James");
        System.out.println(bookingResponse.getBookingid());

        iTestContext.setAttribute("bookingid",bookingResponse.getBookingid());


    }

    //Delete booking
    @Test(groups = "qa",priority = 1)
    @Owner("Akshay")
    @Description("TC#NT1 - Step 1. Delete the booking by Id")
    public void testDeleteBookingbyId(ITestContext iTestContext){
        System.out.println(iTestContext.getAttribute("bookingid"));


        String token = (String)iTestContext.getAttribute("token");
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(403);

    }

    //Update Booking
    @Test(groups = "qa",priority = 2)
    @Owner("Akshay")
    @Description("TC#NT2 - Step 2. Verify update booking by Id")
    public void testUpdateBookingbyId(ITestContext iTestContext){
        System.out.println(iTestContext.getAttribute("bookingid"));


        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token",token);

        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);

        requestSpecification.basePath(basePathPUTPATCH);

        response = RestAssured
                .given(requestSpecification).cookie("token", token)
                .when().body(payloadManager.fullUpdatePayloadAsString()).put();


        validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());

        AssertionsForClassTypes.assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        AssertionsForClassTypes.assertThat(booking.getFirstname()).isEqualTo("Akshay");
        AssertionsForClassTypes.assertThat(booking.getLastname()).isEqualTo("Nomulwar");


        Assert.assertTrue(true);


    }


}
