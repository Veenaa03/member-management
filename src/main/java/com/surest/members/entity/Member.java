package com.surest.members.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(name="date_of_birth", nullable=false)
    private LocalDate dateOfBirth;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(name="created_at", nullable=false)
    private Instant createdAt = Instant.now();

    @Column(name="updated_at", nullable=false)
    private Instant updatedAt = Instant.now();

    // getters/setters...
    // on updates set updatedAt = Instant.now();
    public UUID getId(){ return id; }
    public void setId(UUID id){ this.id = id; }
    public String getFirstName(){ return firstName; }
    public void setFirstName(String firstName){ this.firstName = firstName; }
    public String getLastName(){ return lastName; }
    public void setLastName(String lastName){ this.lastName = lastName; }
    public LocalDate getDateOfBirth(){ return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth){ this.dateOfBirth = dateOfBirth; }
    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }
    public Instant getCreatedAt(){ return createdAt; }
    public void setCreatedAt(Instant createdAt){ this.createdAt = createdAt; }
    public Instant getUpdatedAt(){ return updatedAt; }
    public void setUpdatedAt(Instant updatedAt){ this.updatedAt = updatedAt; }
}
