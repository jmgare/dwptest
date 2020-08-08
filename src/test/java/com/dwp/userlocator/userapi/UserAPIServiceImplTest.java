package com.dwp.userlocator.userapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.dwp.userlocator.User;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@ActiveProfiles("test")
public class UserAPIServiceImplTest {

    @Autowired
    private RestTemplate restTemplate;
 
    @Autowired
    private UserAPIService userAPIService;
 
    private MockRestServiceServer mockServer;
    
    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
    
    @Test                                                                                          
    public void getUsers_singleUserReturned() throws JsonProcessingException {
                
        String json = "[" +
                      "  {\n" + 
                      "    \"id\": 1,\n" + 
                      "    \"first_name\": \"Maurise\",\n" + 
                      "    \"last_name\": \"Shieldon\",\n" + 
                      "    \"email\": \"mshieldon0@squidoo.com\",\n" + 
                      "    \"ip_address\": \"192.57.232.111\",\n" + 
                      "    \"latitude\": 34.003135,\n" + 
                      "    \"longitude\": -117.7228641\n" + 
                      "  }" +
                      "]";

        mockServer.expect(once(), 
          requestTo("https://test-url.com/users"))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));                              
                       
        Set<User> users = userAPIService.getUsers();
        mockServer.verify();
        assertThat(users.size()).isEqualTo(1);
        User user = users.iterator().next();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo("Maurise");
        assertThat(user.getLastName()).isEqualTo("Shieldon");
        assertThat(user.getEmail()).isEqualTo("mshieldon0@squidoo.com");
        assertThat(user.getIpAddress()).isEqualTo("192.57.232.111");
        assertThat(user.getLatitude()).isEqualTo(34.003135);
        assertThat(user.getLongitude()).isEqualTo(-117.7228641);
    }
    
    @Test                                                                                          
    public void getUsers_singleUserReturned_doublesAsStrings() throws JsonProcessingException {
                
        String json = "[" +
                      "  {\n" + 
                      "    \"id\": 1,\n" + 
                      "    \"first_name\": \"Maurise\",\n" + 
                      "    \"last_name\": \"Shieldon\",\n" + 
                      "    \"email\": \"mshieldon0@squidoo.com\",\n" + 
                      "    \"ip_address\": \"192.57.232.111\",\n" + 
                      "    \"latitude\": \"34.003135\",\n" + 
                      "    \"longitude\": \"-117.7228641\"\n" + 
                      "  }" +
                      "]";

        mockServer.expect(once(), 
          requestTo("https://test-url.com/users"))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));                              
                       
        Set<User> users = userAPIService.getUsers();
        mockServer.verify();
        assertThat(users.size()).isEqualTo(1);
        User user = users.iterator().next();
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirstName()).isEqualTo("Maurise");
        assertThat(user.getLastName()).isEqualTo("Shieldon");
        assertThat(user.getEmail()).isEqualTo("mshieldon0@squidoo.com");
        assertThat(user.getIpAddress()).isEqualTo("192.57.232.111");
        assertThat(user.getLatitude()).isEqualTo(34.003135);
        assertThat(user.getLongitude()).isEqualTo(-117.7228641);
    }
    
    @Test                                                                                          
    public void getUsersByCity_multipleUsersReturned() throws JsonProcessingException {
                
        String json = "[" + 
                      "  {\n" + 
                      "    \"id\": 135,\n" + 
                      "    \"first_name\": \"Mechelle\",\n" + 
                      "    \"last_name\": \"Boam\",\n" + 
                      "    \"email\": \"mboam3q@thetimes.co.uk\",\n" + 
                      "    \"ip_address\": \"113.71.242.187\",\n" + 
                      "    \"latitude\": -6.5115909,\n" + 
                      "    \"longitude\": 105.652983\n" + 
                      "  },\n" + 
                      "  {\n" + 
                      "    \"id\": 396,\n" + 
                      "    \"first_name\": \"Terry\",\n" + 
                      "    \"last_name\": \"Stowgill\",\n" + 
                      "    \"email\": \"tstowgillaz@webeden.co.uk\",\n" + 
                      "    \"ip_address\": \"143.190.50.240\",\n" + 
                      "    \"latitude\": -6.7098551,\n" + 
                      "    \"longitude\": 111.3479498\n" + 
                      "  }" +
                      "]";
        
        mockServer.expect(once(), 
          requestTo("https://test-url.com/city/London/users"))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));                              
                       
        Set<User> users = userAPIService.getUsers("London");
        mockServer.verify();
        assertThat(users.size()).isEqualTo(2);
    }
}


