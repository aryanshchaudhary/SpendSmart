//package com.spendsmart.income.service;
//
//import com.spendsmart.income.dto.IncomeRequest;
//import com.spendsmart.income.entity.Income;
//import com.spendsmart.income.exception.ResourceNotFoundException;
//import com.spendsmart.income.repository.IncomeRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class IncomeService {
//
//    private final IncomeRepository repository;
//
//    public IncomeService(IncomeRepository repository) {
//        this.repository = repository;
//    }
//
//    // CREATE
//    public Income addIncome(
//            IncomeRequest request,
//            String email) {
//
//        Income income = new Income();
//
//        income.setSource(request.getSource());
//        income.setAmount(request.getAmount());
//        income.setDescription(request.getDescription());
//        income.setUserEmail(email);
//
//        return repository.save(income);
//    }
//
//    // USER READ 
//    public List<Income> getIncomes(String email) {
//        return repository.findByUserEmail(email);
//    }
//
//    // ADMIN READ ALL
//    public List<Income> getAllIncomes() {
//        return repository.findAll();
//    }
//
//    // UPDATE
//    public Income updateIncome(
//            Long id,
//            IncomeRequest request,
//            String email) {
//
//        Income income =
//                repository.findByIdAndUserEmail(id, email)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Income not found"));
//
//        income.setSource(request.getSource());
//        income.setAmount(request.getAmount());
//        income.setDescription(request.getDescription());
//
//        return repository.save(income);
//    }
//
//    // DELETE
//    public void deleteIncome(
//            Long id,
//            String email) {
//
//        Income income =
//                repository.findByIdAndUserEmail(id, email)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Income not found"));
//
//        repository.delete(income);
//    }
//}


package com.spendsmart.income.service;

import com.spendsmart.income.dto.IncomeEvent;
import com.spendsmart.income.dto.IncomeRequest;
import com.spendsmart.income.entity.Income;
import com.spendsmart.income.exception.ResourceNotFoundException;
import com.spendsmart.income.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository repository;
    private final IncomeProducer producer;

    public IncomeService(IncomeRepository repository,
                         IncomeProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    // CREATE
    public Income addIncome(IncomeRequest request, String email) {

        System.out.println("🔥 ADD INCOME CALLED");

        Income income = new Income();

        income.setSource(request.getSource());
        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());
        income.setUserEmail(email);

        Income savedIncome = repository.save(income);

        // 🔥 SEND EVENT
        IncomeEvent event = new IncomeEvent(
                savedIncome.getSource(),
                savedIncome.getAmount(),
                savedIncome.getDescription(),
                savedIncome.getUserEmail()
        );

        producer.sendIncomeEvent(event);

        return savedIncome;
    }

    public List<Income> getIncomes(String email) {
        return repository.findByUserEmail(email);
    }

    public List<Income> getAllIncomes() {
        return repository.findAll();
    }

    public Income updateIncome(Long id, IncomeRequest request, String email) {

        Income income = repository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found"));

        income.setSource(request.getSource());
        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());

        return repository.save(income);
    }

    public void deleteIncome(Long id, String email) {

        Income income = repository.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found"));

        repository.delete(income);
    }
}