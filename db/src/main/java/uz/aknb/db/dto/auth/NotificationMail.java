package uz.aknb.db.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationMail {

    private String subject;

    private String recipient;

    private String body;

    public NotificationMail(String subject, String recipient){
        this.subject = subject;
        this.recipient = recipient;
    }

    public NotificationMail(String subject, String recipient, String body){
        this.subject = subject;
        this.recipient = recipient;
        this.body = body;
    }
}
