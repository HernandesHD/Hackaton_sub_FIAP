package com.hernandes.andrade.fiap.hackatonfiasub.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_exchange_proposal")
@EntityListeners(AuditingEntityListener.class)
public class ExchangeProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requester_id")
    //@JsonBackReference // Marca a referência inversa para evitar loop de serialização
    private User requester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    //@JsonManagedReference // Marca a referência gerenciadora
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    private Game requestedGame;

    @ManyToOne(fetch = FetchType.EAGER)
    private Game offeredGame;

    @Column(nullable = false)
    private String status;

}
