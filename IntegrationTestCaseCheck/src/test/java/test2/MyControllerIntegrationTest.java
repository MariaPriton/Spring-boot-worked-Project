package test2;


import com.example.TestCaseCheck.Repo.UserRepository;
import com.example.TestCaseCheck.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)*/
@SpringBootApplication
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DataJpaTest
@BootstrapWith(value=org.springframework.boot.test.context.SpringBootTestContextBootstrapper.class)
public class MyControllerIntegrationTest {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/products");
    }



    @Test
    public void testAddProduct() {
        User product = new User();
        product.setId(6);
        product.setName("Robin");
        product.setAge(15);
        product.setAddress("Surya");
        User response = restTemplate.postForObject(baseUrl, product, User.class);
        assertEquals("Robin", response.getName());
        assertEquals(1, userRepository.findAll().size());
    }

    /*@Test
    public void testMyController() throws Exception {
        // Arrange
        User myModel = new User();

        // Act
        ResponseEntity<User> response = restTemplate.getForEntity("/api/my-endpoint",myModel, User.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(myModel);
    }*/

}
