package com.veezy.todoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY, mappedBy = "roles", cascade = {CascadeType.REFRESH,
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<User> users;

    public void addUser(User theUser) {
        if (users == null) {
            users = new HashSet<>();
        }
        users.add(theUser);
    }
}
