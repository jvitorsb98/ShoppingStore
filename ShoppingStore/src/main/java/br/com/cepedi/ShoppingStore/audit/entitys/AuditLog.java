package br.com.cepedi.ShoppingStore.audit.entitys;

import java.util.Date;

import br.com.cepedi.ShoppingStore.audit.record.input.DataRegisterAudit;
import br.com.cepedi.ShoppingStore.security.model.entitys.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull(message = "EventName must not be null") // Validação no nível da aplicação
    @Size(min = 5, max = 50, message = "EventName must be between 5 and 50 characters long")
    @Column(unique = true, nullable = false) // Restrições a nível de banco de dados
    private String eventName;
    private String eventDescription;
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String affectedResource;
    private String origin;

    public AuditLog(DataRegisterAudit data, User user){
        this.eventName = data.eventName();
        this.eventDescription = data.eventDescription();
        this.timestamp = new Date();
        this.user = user;
        this.affectedResource = data.affectedResource();
        this.origin = data.origin();
    }

}