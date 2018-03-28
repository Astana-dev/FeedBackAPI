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
@Table(name = "message", catalog = "feedback")
@FetchProfiles(value = {
//        @FetchProfile(name = "domain", fetchOverrides = {@FetchProfile.FetchOverride(entity = EntityFeedBackMessage.class, association = "domain", mode = FetchMode.JOIN)}),
        @FetchProfile(name = "session", fetchOverrides = {@FetchProfile.FetchOverride(entity = EntityFeedBackMessage.class, association = "session", mode = FetchMode.JOIN)})
})
public class EntityFeedBackMessage {
//    public static final String FETCH_PROFILE_DOMAIN = "domain";
    public static final String FETCH_PROFILE_SESSION = "session";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "domain", referencedColumnName = "id", nullable = false)
//    @JsonIgnore
//    private EntityFeedBackDomain domain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private EntityFeedBackSession session;

    @Column(name = "user")
    private String user;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private Date dateCreate;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

//    public EntityFeedBackDomain getDomain() {
//        return domain;
//    }
//    public void setDomain(EntityFeedBackDomain domain) {
//        this.domain = domain;
//    }

    public EntityFeedBackSession getSession() {
        return session;
    }
    public void setSession(EntityFeedBackSession session) {
        this.session = session;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
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
