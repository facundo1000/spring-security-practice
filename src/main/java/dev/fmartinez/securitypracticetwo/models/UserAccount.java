package dev.fmartinez.securitypracticetwo.models;

import dev.fmartinez.securitypracticetwo.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "ACCOUNT")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @Pattern(regexp = "[a-zA-Z]+@[a-zA-Z]+.{1}[a-z]+")
    private String email;

    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "TBL_ACCOUNT_ROLE",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role;

    @PrePersist
    public void init() {
        if (Objects.isNull(enabled)) {
            this.enabled = true;
        }
    }
}
