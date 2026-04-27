// RecurringService.java

package com.spendsmart.recurring.service;

import com.spendsmart.recurring.client.ExpenseClient;
import com.spendsmart.recurring.client.IncomeClient;
import com.spendsmart.recurring.dto.RecurringRequest;
import com.spendsmart.recurring.entity.Recurring;
import com.spendsmart.recurring.repository.RecurringRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecurringService {

    private final RecurringRepository repo;
    private final ExpenseClient expenseClient;
    private final IncomeClient incomeClient;

    public RecurringService(RecurringRepository repo,
                            ExpenseClient expenseClient,
                            IncomeClient incomeClient) {
        this.repo = repo;
        this.expenseClient = expenseClient;
        this.incomeClient = incomeClient;
    }

    public Recurring save(Recurring recurring) {
        return repo.save(recurring);
    }

    public List<Recurring> getAll(String email) {
        return repo.findByUserEmail(email);
    }

    public Recurring update(Long id,
                            RecurringRequest request,
                            String email) {

        Recurring r = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Recurring not found"));

        if (!r.getUserEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        r.setType(request.getType());
        r.setTitle(request.getTitle());
        r.setAmount(request.getAmount());
        r.setCategory(request.getCategory());

        return repo.save(r);
    }

    public void delete(Long id, String email) {

        Recurring r = repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Recurring not found"));

        if (!r.getUserEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        repo.delete(r);
    }

    public String trigger(String email) {

        List<Recurring> list = repo.findByUserEmail(email);

        for (Recurring r : list) {

            Map<String, Object> data = new HashMap<>();

            if (r.getType().equalsIgnoreCase("EXPENSE")) {

                data.put("title", r.getTitle());
                data.put("amount", r.getAmount());
                data.put("category", r.getCategory());

                expenseClient.createExpense(data, email);

            } else {

                data.put("source", r.getTitle());
                data.put("amount", r.getAmount());
                data.put("description", "Recurring income");

                incomeClient.createIncome(data, email);
            }
        }

        return "Recurring transactions executed";
    }
}