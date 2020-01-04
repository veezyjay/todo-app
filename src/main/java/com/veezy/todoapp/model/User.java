package com.veezy.todoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_name")
    private String username;

    private String status;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "taskCreator", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @JsonIgnore
    @ManyToMany(fetch=FetchType.EAGER, cascade= {CascadeType.REFRESH,
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public void addRole(Role theRole) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        theRole.addUser(this);
        roles.add(theRole);
    }
}
