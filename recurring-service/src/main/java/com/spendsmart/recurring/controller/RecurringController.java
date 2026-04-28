package com.spendsmart.recurring.controller;

import com.spendsmart.recurring.dto.RecurringRequest;
import com.spendsmart.recurring.dto.TriggerResponse;
import com.spendsmart.recurring.entity.Recurring;
import com.spendsmart.recurring.service.RecurringService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recurring")
@CrossOrigin(origins = "http://localhost:4200")
public class RecurringController {

    private final RecurringService service;

    public RecurringController(RecurringService service) {
        this.service = service;
    }

    @PostMapping
    public Recurring create(@RequestBody RecurringRequest request,
                            @RequestHeader("X-User-Email") String email) {

        Recurring r = new Recurring();
        r.setType(request.getType());
        r.setTitle(request.getTitle());
        r.setAmount(request.getAmount());
        r.setCategory(request.getCategory());
        r.setUserEmail(email);

        return service.save(r);
    }

    @GetMapping
    public List<Recurring> getAll(
            @RequestHeader("X-User-Email") String email) {

        return service.getAll(email);
    }

    @PutMapping("/{id}")
    public Recurring update(
            @PathVariable Long id,
            @RequestBody RecurringRequest request,
            @RequestHeader("X-User-Email") String email) {

        return service.update(id, request, email);
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id,
            @RequestHeader("X-User-Email") String email) {

        service.delete(id, email);
        return "Recurring deleted successfully";
    }

    @PostMapping("/trigger")
    public TriggerResponse trigger(
            @RequestHeader("X-User-Email") String email) {

        return new TriggerResponse(service.trigger(email));
    }
}