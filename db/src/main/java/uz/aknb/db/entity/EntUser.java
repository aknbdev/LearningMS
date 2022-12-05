package uz.aknb.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE users SET deleted = TRUE WHERE id = ?", check = ResultCheckStyle.COUNT)
public class EntUser extends EntBase{

    @Size(max = 50)
    @Column(name = "firstname")
    private String firstName;

    @Size(max = 50)
    @Column(name = "lastname")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Size(max = 64)
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "role")
            }
    )
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<EntRole> roles = new HashSet<>();

    @Column(name = "verified", columnDefinition = "boolean default false")
    private Boolean verified = Boolean.FALSE;
}
