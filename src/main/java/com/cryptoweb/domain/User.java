package com.cryptoweb.domain;


import com.cryptoweb.domain.enums.Role;
import com.cryptoweb.domain.enums.State;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
@Entity
@Table(name = "users_tbl")
public class User extends BaseEntity implements UserDetails {
    public static final int LENGTH = 100;
    public static final boolean BUN_NULL = false;

    @Column(nullable = BUN_NULL, length = LENGTH, unique = true)
    private String username;
    @Column(name = "pass", nullable = BUN_NULL, length = LENGTH)
    private String password;

    @Email(message = "errors.user.email.value.email_not_correct")
    @Column(name = "email", nullable = BUN_NULL, length = LENGTH) //unique уникальное значение
    private String email;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_enum")
    private State state;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user2roles_tbl",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            foreignKey = @ForeignKey(name = "users2roles_user_fk"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "roles_enum"}))
    @Enumerated(EnumType.STRING)
    @Column(name = "roles_enum", length = LENGTH)
    private Set<Role> roles = new HashSet<>();


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public boolean isUserAut() {
        return roles.contains(Role.USER);
    }



}
