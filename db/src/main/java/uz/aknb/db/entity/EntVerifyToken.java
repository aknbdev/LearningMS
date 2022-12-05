package uz.aknb.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "verify_tokens")
public class EntVerifyToken {

        @Id
        @SequenceGenerator(
                name = "verify_token_sequence",
                sequenceName = "verify_token_sequence",
                allocationSize = 1
        )
        @GeneratedValue(
                strategy = GenerationType.SEQUENCE,
                generator = "verify_token_sequence"
        )
        private Long id;

        @Column(name = "token", nullable = false, length = 64)
        private String token;

        @Column(name = "created_at", nullable = false)
        private LocalDateTime createdAt;

        @Column(name = "expires_date", nullable = false)
        private LocalDateTime expiresDate;

        @Column(name = "confirmed_date")
        private LocalDateTime confirmedDate;

        @ManyToOne
        @JoinColumn(nullable = false, name = "user_id")
        private EntUser user;

        public EntVerifyToken(String token,
                              EntUser user,
                              LocalDateTime expiresDate) {

                this.token = token;
                this.user = user;
                this.expiresDate = expiresDate;
                this.createdAt = LocalDateTime.now();
        }
}
