package com.example.TestCaseCheck.Repo;

import com.example.TestCaseCheck.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByAddress(String address);
}
