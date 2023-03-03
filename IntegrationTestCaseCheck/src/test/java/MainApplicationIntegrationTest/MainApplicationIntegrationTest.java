package MainApplicationIntegrationTest;

import com.example.TestCaseCheck.Repo.UserRepository;
import com.example.TestCaseCheck.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootApplication
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MainApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /*@Autowired
    private UserRepository userRepository;*/

    @Test
    void contextLoads() {
    }
    @MockBean
    private UserRepository userRepository;


    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        restTemplate = new TestRestTemplate();
    }

    @Value("${spring.dataPostURL}")
    private String dataPostURL;

    @Test
    public void testCreateUser() {
        // Given

        User userData=new User();
        userData.setId(6);
        userData.setName("Robin");
        userData.setAge(15);
        userData.setAddress("Surya");

        String checkURL = dataPostURL;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> entity = new HttpEntity<User>(userData,headers);

        RestTemplate template = new RestTemplate();

        //ResponseEntity<String> response = template.postForEntity(checkURL, entity, User.class);
        // When
        ResponseEntity<User> response = template.postForEntity(
                checkURL, userData, User.class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        User createdUser = response.getBody();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getId()).isEqualTo(userData.getId());
        assertThat(createdUser.getName()).isEqualTo(userData.getName());
        assertThat(createdUser.getAge()).isEqualTo(userData.getAge());
        assertThat(createdUser.getAddress()).isEqualTo(userData.getAddress());
        // Verify that the user was persisted in the database


        Optional<User> persistedUser = userRepository.findById(createdUser.getId());
        assertThat(persistedUser).isPresent();
        assertThat(persistedUser.get().getId()).isEqualTo(createdUser.getId());
        assertThat(persistedUser.get().getName()).isEqualTo(createdUser.getName());
        assertThat(persistedUser.get().getAge()).isEqualTo(createdUser.getAge());
        assertThat(persistedUser.get().getAddress()).isEqualTo(createdUser.getAddress());





    }
}
