package com.example.TestCaseCheck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.TestCaseCheck.Repo.UserRepository;
import com.example.TestCaseCheck.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class TestCaseCheckApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private UserService service;

	@MockBean
	private UserRepository repository;

	@Test
	public void getUsersTest() {

		when(repository.findAll()).thenReturn(Stream
				.of(new User(1, "Priton", 15, "abcd"), new User(2, "Priton", 15, "abcd"),new User(3, "Robin", 15, "abcd"),new User(1, "King", 16, "efgh")).collect(Collectors.toList()));
		assertEquals(4, service.getUsers().size());
	}



	@Test
	public void getUserbyAddressTest() {
		String address = "efgh";
		when(repository.findByAddress(address))
				.thenReturn(Stream.of(new User(5, "King", 16, "efgh")).collect(Collectors.toList()));
		assertEquals(1, service.getUserbyAddress(address).size());
	}


	@Test
	public void saveUserTest() {
		User user = new User(1, "Priton", 15, "abcd");
		when(repository.save(user)).thenReturn(user);
		assertEquals(user, service.addUser(user));
	}



	@Test
	public void deleteUserTest() {
		User user = new User(1, "Priton", 15, "abcd");
		service.deleteUser(user);
		verify(repository, times(1)).delete(user);
	}



}
