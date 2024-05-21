package br.com.cepedi.ShoppingStore.security.model.entitys;

import br.com.cepedi.ShoppingStore.model.records.mail.input.DataRegisterMail;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "mail")
public class Mail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    private String from;
    private String to;
    private String content;
    private String subject;


    public Mail(DataRegisterMail data){
        this.from = data.from();
        this.to = data.to();
        this.content = data.content();
        this.subject = data.subject();
    }
}
