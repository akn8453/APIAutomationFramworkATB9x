package com.akshaynomulwar.tests.crud;

import com.akshaynomulwar.base.BaseTest;
import com.akshaynomulwar.endpoints.APIConstants;
import com.akshaynomulwar.pojos.BookingResponse;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.akshaynomulwar.base.BaseTest;

public class test_CreateBooking extends BaseTest {

    @Owner("Promode")
    @TmsLink("https://google.com")
    @Link(name = "Link to TC", url = "https://bugz.atlassian.net/browse/RBT-4")
    @Issue("JIRA_RBT-4")
    @Description("Verify that POST request is working fine.")
    @Test(groups = "qa")
    public void testVerifyCreateBookingPOST01() {
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);

        response = RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString()).post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "James");

    }



}
