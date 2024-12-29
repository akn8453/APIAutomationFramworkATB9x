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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestIntegrationCase2 extends BaseTest {

    //Get Booking
    @Test(groups = {"qc"}, priority = 1)
    @Owner("Akshay")
    @Description("TC#INT1 Step 1 : Get all Booking")
    public void testGetABookingFromAllBooking(ITestContext iTestContext) {
        String basePathAllGet = APIConstants.CREATE_UPDATE_BOOKING_URL;

        requestSpecification.basePath(basePathAllGet);
        response = RestAssured.given(requestSpecification)
                .when().log().all().get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        List<BookingResponse> allBookingResponse = payloadManager.getABooking(response.asString());


    }

    @Test(groups = "qa",priority = 2)
    @Owner("Akshay")
    @Description("TC#NT2 - Step 2. Delete the booking by Id")
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


}