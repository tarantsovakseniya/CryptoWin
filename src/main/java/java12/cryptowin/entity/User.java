package java12.cryptowin.entity;

import java12.cryptowin.entity.enumeration.IconType;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "photo_url")
    private IconType iconType;

    @Column(name = "active")
    private int active = 1;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany (mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Subscription> subscriptions = new HashSet<>();

    public boolean hasRole(String roleName) {
        for(Role role: roles) {
            if (role.getRole().equals(roleName)) {
                return true;
            }
        }

        return false;
    }
}
