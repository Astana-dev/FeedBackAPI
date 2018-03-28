package entity;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.FetchProfiles;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by Nurzhan on 31.10.2017.
 */

@Entity
@Table(name = "domain", catalog = "feedback")
@FetchProfiles(value = {
//        @FetchProfile(name = "user", fetchOverrides = {@FetchProfile.FetchOverride(entity = EntityFeedBackDomain.class, association = "user", mode = FetchMode.JOIN)}),
//        @FetchProfile(name = "message", fetchOverrides = {@FetchProfile.FetchOverride(entity = EntityFeedBackDomain.class, association = "message", mode = FetchMode.JOIN)})
})
public class EntityFeedBackDomain {
    public static final String FETCH_PROFILE_USER = "user";
//    public static final String FETCH_PROFILE_MESSAGE = "message";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "site", nullable = false)
    private String site;

    @Column(name = "date_create")
    private Date dateCreate;

    @OneToMany(mappedBy = "domain", fetch = FetchType.LAZY)
    private Set<EntityFeedBackUser> user;

//    @OneToMany(mappedBy = "domain", fetch = FetchType.LAZY)
//    private Set<EntityFeedBackMessage> message;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
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

    public Set<EntityFeedBackUser> getUser() {
        return user;
    }
    public void setUser(Set<EntityFeedBackUser> user) {
        this.user = user;
    }

//    public Set<EntityFeedBackMessage> getMessage() {
//        return message;
//    }
//    public void setMessage(Set<EntityFeedBackMessage> message) {
//        this.message = message;
//    }
}
