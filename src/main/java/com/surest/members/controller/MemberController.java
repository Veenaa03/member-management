package com.surest.members.controller;

import com.surest.members.entity.Member;
import com.surest.members.service.MemberService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.UUID;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService service;
    public MemberController(MemberService service){ this.service = service; }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Page<Member> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String sort,
            @RequestParam(defaultValue = "") String firstName,
            @RequestParam(defaultValue = "") String lastName) {

        // pass all five parameters to service
        return service.list(page, size, sort, firstName, lastName);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Member> get(@PathVariable UUID id) {
        Member member = service.getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"));
        return ResponseEntity.ok(member);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Member> create(@RequestBody Member m){
        Member created = service.create(m);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Member update(@PathVariable UUID id, @RequestBody Member m){
        return service.update(id, m);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
