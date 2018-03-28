package entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Nurzhan on 02.11.2017.
 */
@Entity
@Table(name = "sessions", catalog = "feedback")
public class EntityFeedBackSession {
    public static final String FETCH_PROFILE_MESSAGE = "message";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "domain", nullable = false)
    private int domain_id;

    @Column(name = "user")
    private String user;

    @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
    private Set<EntityFeedBackMessage> message;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getDomain_id() {
        return domain_id;
    }
    public void setDomain_id(int domain_id) {
        this.domain_id = domain_id;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public Set<EntityFeedBackMessage> getMessage() {
        return message;
    }
    public void setMessage(Set<EntityFeedBackMessage> message) {
        this.message = message;
    }
}
