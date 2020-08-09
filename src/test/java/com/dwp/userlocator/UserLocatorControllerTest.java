package com.dwp.userlocator;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserLocatorController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
public class UserLocatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserLocatorService service;

    private User user;

    @BeforeEach
    public void setup() {
        int id = 396;
        String firstName = "Terry";
        String lastName = "Stowgill";
        String email = "tstowgillaz@webeden.co.uk";
        String ipAddress = "143.190.50.240";
        double latitude = -6.7098551;
        double longitude = 111.3479498;
        
        user = new User(id, firstName, lastName, email, ipAddress, latitude, longitude);
    }
    
    @Test
    public void test_locator_users_defaults_singleUser() throws Exception {
        Optional<String> cityOpt = Optional.empty();
        Optional<Double> distanceOpt = Optional.empty();
        
        when(service.getUsers(cityOpt, distanceOpt)).thenReturn(Stream.of(user).collect(Collectors.toSet()));

        this.mockMvc.perform(get("/locator/users")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").value(user.getId()))
            .andExpect(jsonPath("$[0].first_name").value(user.getFirstName()))
            .andExpect(jsonPath("$[0].last_name").value(user.getLastName()))
            .andExpect(jsonPath("$[0].email").value(user.getEmail()))
            .andExpect(jsonPath("$[0].ip_address").value(user.getIpAddress()))
            .andExpect(jsonPath("$[0].latitude").value(user.getLatitude()))
            .andExpect(jsonPath("$[0].longitude").value(user.getLongitude()))
            .andDo(document("users", requestParameters(
                parameterWithName("city").description(
                        "The city for which users should be queried. Default London.").optional(),
                parameterWithName("distance").description(
                        "The distance in miles from the city to include user co-ordinates. Default 50 miles.").optional()
            ), responseFields(
                fieldWithPath("[].id").description("The unique identifier of the user"),
                fieldWithPath("[].first_name").description("The first name of the user."),
                fieldWithPath("[].last_name").description("The last name of the user."),
                fieldWithPath("[].email").description("The email of the user."),
                fieldWithPath("[].ip_address").description("The IP address of the user."),
                fieldWithPath("[].latitude").description("The latitude of the user."),
                fieldWithPath("[].longitude").description("The longitude of the user."))));
    }
    
    @Test
    public void test_locator_users_cityAndDistanceSupplied() throws Exception {
        String city = "London";
        double distance = 12.8;
        Optional<String> cityOpt = Optional.of(city);
        Optional<Double> distanceOpt = Optional.of(distance);

        when(service.getUsers(cityOpt, distanceOpt)).thenReturn(Stream.of(user).collect(Collectors.toSet()));
        
        this.mockMvc.perform(get("/locator/users").param("city", city).param("distance", distance + "")).
            andDo(print()).
            andExpect(status().isOk()).
            andExpect(jsonPath("$", hasSize(1)));
    }
    
    @Test
    public void test_locator_users_distanceLessThanZero_badRequest() throws Exception {
        String city = "London";
        double distance = -1.8;
        Optional<String> cityOpt = Optional.of(city);
        Optional<Double> distanceOpt = Optional.of(distance);

        when(service.getUsers(cityOpt, distanceOpt)).thenReturn(Stream.of(user).collect(Collectors.toSet()));
        
        this.mockMvc.perform(get("/locator/users").param("city", city).param("distance", distance + "")).
            andExpect(status().isBadRequest()).
            andDo(document("badRequest"));
    }
    
    @Test
    public void test_locator_users_serviceThrowsException_notFound() throws Exception {
        String city = "Leeds";
        double distance = 11.8;
        Optional<String> cityOpt = Optional.of(city);
        Optional<Double> distanceOpt = Optional.of(distance);

        when(service.getUsers(cityOpt, distanceOpt)).thenThrow(new UserLocatorException());
        
        this.mockMvc.perform(get("/locator/users").param("city", city).param("distance", distance + "")).
            andExpect(status().isNotFound()).
            andDo(document("notFound"));

    }
}
