package uz.aknb.db.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class EntRole implements GrantedAuthority {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;

    @Override
    public String getAuthority() {
        return role.name();
    }
}
