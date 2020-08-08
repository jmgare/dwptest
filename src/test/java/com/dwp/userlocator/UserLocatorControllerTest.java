package com.dwp.userlocator;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    public void testGetUsers_defaults() throws Exception {
        Optional<String> cityOpt = Optional.empty();
        Optional<Double> distanceOpt = Optional.empty();

        int id = 396;
        String firstName = "Terry";
        String lastName = "Stowgill";
        String email = "tstowgillaz@webeden.co.uk";
        String ipAddress = "143.190.50.240";
        double latitude = -6.7098551;
        double longitude = 111.3479498;
        
        User user = new User(id, firstName, lastName, email, ipAddress, latitude, longitude);
        
        when(service.getUsers(cityOpt, distanceOpt)).thenReturn(Stream.of(user).collect(Collectors.toSet()));

        this.mockMvc.perform(get("/locator/users")).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].id").value(id))
            .andExpect(jsonPath("$[0].first_name").value(firstName))
            .andExpect(jsonPath("$[0].last_name").value(lastName))
            .andExpect(jsonPath("$[0].email").value(email))
            .andExpect(jsonPath("$[0].ip_address").value(ipAddress))
            .andExpect(jsonPath("$[0].latitude").value(latitude))
            .andExpect(jsonPath("$[0].longitude").value(longitude));
    }
}
