package com.surest.members.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    @Column(unique=true, nullable=false)
    private String username;

    @Column(name="password_hash", nullable=false)
    private String passwordHash;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    // getters/setters...
    public UUID getId(){ return id; }
    public void setId(UUID id){ this.id = id; }
    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }
    public String getPasswordHash(){ return passwordHash; }
    public void setPasswordHash(String passwordHash){ this.passwordHash = passwordHash; }
    public Role getRole(){ return role; }
    public void setRole(Role role){ this.role = role; }
}
