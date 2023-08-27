package com.yen.SpringAssignmentSystem.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// https://youtu.be/KMecT1HBm4c?si=ZvXY7O_2RNviIrYq&t=180

@Entity
@Table(name="users") // rename table as "users" in DB, since User is a reserved name in Mysql
@Data
@ToString
public class User implements UserDetails { // in order to make user can be return in UserDetailService : https://youtu.be/TOQjvskdl3g?si=6OwdEu-esgjdk6B4&t=772

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate cohortStartDate;
    private String password;
    @ElementCollection(targetClass=Assignment.class) // https://stackoverflow.com/questions/3774198/org-hibernate-mappingexception-could-not-determine-type-for-java-util-list-at
    private List<Assignment> assignments =  new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return getAuthorities();
        return null;
    }

    @Override
    public String getUsername() {
        return getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
