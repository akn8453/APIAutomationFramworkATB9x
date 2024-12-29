package com.akshaynomulwar.tests.Integration;

import com.akshaynomulwar.base.BaseTest;
import com.akshaynomulwar.endpoints.APIConstants;
import com.akshaynomulwar.pojos.Booking;
import com.akshaynomulwar.pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestIntegrationFlow extends BaseTest {

    // TEST INTEGRATION CASE 1
    // 1. Create a booking -> Booking id
    // 2. Create token -> token
    // 3. Verify that the create booking is working - Get request to booking ID
    // 4. Update the booking (BookingID token) - need to get the token, bookingid from the above request
    // 5. Delete the booking

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

    @Test(groups = "qa",priority = 2)
    @Owner("Akshay")
    @Description("TC#NT1 - Step 2. Verify that the booking By ID")
    public void testVerifyBookingId(ITestContext iTestContext){
        System.out.println(iTestContext.getAttribute("bookingid"));
        Assert.assertTrue(true);

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        // GET Request - to verify that the firstname after creation is James
        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL+"/" + bookingid;
        System.out.println(basePathGET);

        requestSpecification.basePath(basePathGET);
        response = RestAssured
                .given(requestSpecification)
                .when().get();
        validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking.getFirstname()).isEqualTo("James");


    }

    @Test(groups = "qa",priority = 3)
    @Owner("Akshay")
    @Description("TC#NT1 - Step 3. Verify update booking by Id")
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

        assertThat(booking.getFirstname()).isNotNull().isNotBlank();
        assertThat(booking.getFirstname()).isEqualTo("Akshay");
        assertThat(booking.getLastname()).isEqualTo("Nomulwar");


        Assert.assertTrue(true);


    }

    @Test(groups = "qa",priority = 4)
    @Owner("Akshay")
    @Description("TC#NT1 - Step 4. Delete the booking by Id")
    public void testDeleteBookingbyId(ITestContext iTestContext){
        System.out.println(iTestContext.getAttribute("bookingid"));


        String token = (String)iTestContext.getAttribute("token");
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);

    }



}
