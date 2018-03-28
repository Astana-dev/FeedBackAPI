package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.FetchProfiles;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nurzhan on 31.10.2017.
 */
@Entity
@Table(name = "user", catalog = "feedback")
@FetchProfiles(value = {
        @FetchProfile(name = "domain", fetchOverrides = {@FetchProfile.FetchOverride(entity = EntityFeedBackUser.class, association = "domain", mode = FetchMode.JOIN)})
})
public class EntityFeedBackUser {
    public static final String FETCH_PROFILE_DOMAIN = "domain";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private EntityFeedBackDomain domain;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "date_create")
    private Date dateCreate;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public EntityFeedBackDomain getDomain() {
        return domain;
    }
    public void setDomain(EntityFeedBackDomain domain) {
        this.domain = domain;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public String getDateCreateFormat() {
        if (dateCreate == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(dateCreate);
    }
    public Date getDateCreate() {
        return dateCreate;
    }
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
}
