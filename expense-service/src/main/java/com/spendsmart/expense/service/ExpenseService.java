//package com.spendsmart.expense.service;
//
//import com.spendsmart.expense.dto.ExpenseRequest;
//import com.spendsmart.expense.entity.Expense;
//import com.spendsmart.expense.exception.ResourceNotFoundException;
//import com.spendsmart.expense.repository.ExpenseRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class ExpenseService {
//
//    private final ExpenseRepository repository;
//
//    public ExpenseService(ExpenseRepository repository) {
//        this.repository = repository;
//    }
//
//    // CREATE
//    public Expense addExpense(
//            ExpenseRequest request,
//            String email) {
//
//        Expense expense = new Expense();
//
//        expense.setTitle(request.getTitle());
//        expense.setAmount(request.getAmount());
//        expense.setCategory(request.getCategory());
//        expense.setUserEmail(email);
//
//        return repository.save(expense);
//    }
//
//    // USER READ
//    public List<Expense> getExpenses(String email) {
//        return repository.findByUserEmail(email);
//    }
//
//    // ADMIN READ ALL
//    public List<Expense> getAllExpenses() {
//        return repository.findAll();
//    }
//
//    // UPDATE
//    public Expense updateExpense(
//            Long id,
//            ExpenseRequest request,
//            String email) {
//
//        Expense expense =
//                repository.findByIdAndUserEmail(id, email)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Expense not found"));
//
//        expense.setTitle(request.getTitle());
//        expense.setAmount(request.getAmount());
//        expense.setCategory(request.getCategory());
//
//        return repository.save(expense);
//    }
//
//    // DELETE
//    public void deleteExpense(
//            Long id,
//            String email) {
//
//        Expense expense =
//                repository.findByIdAndUserEmail(id, email)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Expense not found"));
//
//        repository.delete(expense);
//    }
//
//    // SUMMARY
//    public Map<String, Double> getCategorySummary(
//            String email) {
//
//        List<Expense> expenses =
//                repository.findByUserEmail(email);
//
//        return expenses.stream()
//                .collect(Collectors.groupingBy(
//                        Expense::getCategory,
//                        Collectors.summingDouble(
//                                Expense::getAmount
//                        )
//                ));
//    }
//}

package com.spendsmart.expense.service;

import com.spendsmart.expense.dto.ExpenseEvent;
import com.spendsmart.expense.dto.ExpenseRequest;
import com.spendsmart.expense.entity.Expense;
import com.spendsmart.expense.exception.ResourceNotFoundException;
import com.spendsmart.expense.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;
    private final ExpenseProducer producer;

    public ExpenseService(ExpenseRepository repository,
                          ExpenseProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    // CREATE
    public Expense addExpense(ExpenseRequest request, String email) {

    	System.out.println("🔥 ADD EXPENSE CALLED");
    	
        Expense expense = new Expense();
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setUserEmail(email);

        Expense savedExpense = repository.save(expense);

        // SEND EVENT
        ExpenseEvent event = new ExpenseEvent(
                savedExpense.getTitle(),
                savedExpense.getAmount(),
                savedExpense.getCategory(),
                savedExpense.getUserEmail()
        );

        producer.sendEvent(event);

        return savedExpense;
    }

    public List<Expense> getExpenses(String email) {
        return repository.findByUserEmail(email);
    }

    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    public Expense updateExpense(Long id, ExpenseRequest request, String email) {

        Expense expense = repository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());

        return repository.save(expense);
    }

    public void deleteExpense(Long id, String email) {

        Expense expense = repository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        repository.delete(expense);
    }

    public Map<String, Double> getCategorySummary(String email) {

        List<Expense> expenses = repository.findByUserEmail(email);

        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }
}