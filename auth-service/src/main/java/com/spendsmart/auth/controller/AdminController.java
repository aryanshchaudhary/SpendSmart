package com.spendsmart.auth.controller;

import com.spendsmart.auth.entity.User;
import com.spendsmart.auth.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // USERS

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // STATS

    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        RestTemplate rest = new RestTemplate();

        long totalUsers = userRepository.count();

        long totalExpenses = 0;
        long totalIncome = 0;
        long totalBudgets = 0;

        try {
            Object[] expenses =
                rest.getForObject(
                    "http://localhost:8082/api/expenses/admin",
                    Object[].class
                );

            totalExpenses = expenses.length;
        } catch (Exception e) {}

        try {
            Object[] incomes =
                rest.getForObject(
                    "http://localhost:8083/api/incomes/admin",
                    Object[].class
                );

            totalIncome = incomes.length;
        } catch (Exception e) {}

        try {
            Object[] budgets =
                rest.getForObject(
                    "http://localhost:8085/api/budgets/admin",
                    Object[].class
                );

            totalBudgets = budgets.length;
        } catch (Exception e) {}

        Map<String, Object> map = new HashMap<>();

        map.put("totalUsers", totalUsers);
        map.put("totalExpenses", totalExpenses);
        map.put("totalIncome", totalIncome);
        map.put("totalBudgets", totalBudgets);

        return map;
    }

    //DELETE USER

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }
}