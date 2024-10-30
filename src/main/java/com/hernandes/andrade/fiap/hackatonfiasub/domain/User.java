package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import com.fasterxml.jackson.annotation.*;
import com.hernandes.andrade.fiap.hackatonfiasub.usecase.ExchangePreference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "proposals", "games"})
public class User implements UserDetails, Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incremento no banco
    private Integer id;

    @Column(nullable = false, length = 100) // Campo obrigatório com tamanho máximo
    private String name;

    @Column(nullable = false, unique = true, length = 100) // Email único e obrigatório
    private String email;

    @Column(nullable = false) // Senha obrigatória
    private String password;

    private boolean accountLocket;
    private boolean enabled;

    @Enumerated(EnumType.STRING) // Armazena o enum como String no banco
    @Column(nullable = false)
    private ExchangeType preference;

    @ManyToMany(fetch = FetchType.EAGER) // Lista de roles (pode carregar automaticamente)
    private List<UserRole> roles = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Game> games = new ArrayList<>();

    @OneToMany(mappedBy = "requester", fetch = FetchType.LAZY)
    private List<ExchangeProposal> proposals = new ArrayList<>();

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonBackReference // Marca a referência inversa
    private List<ExchangeProposal> ownedProposals = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocket;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }

}
