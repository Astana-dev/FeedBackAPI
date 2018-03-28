package servicesImpl;

import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.sun.org.apache.xml.internal.security.utils.IgnoreAllErrorHandler;
import entity.EntityFeedBackDomain;
import entity.EntityFeedBackMessage;
import entity.EntityFeedBackSession;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.Delete;
import org.hibernate.type.ClassType;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import services.dataService;
import sun.awt.event.IgnorePaintEvent;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Nurzhan on 11.10.2017.
 */

@Repository
@Transactional
public class dataServiceImpl implements dataService {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Object verification(HashMap map) {
        Session ses = sessionFactory.openSession();
        String sql = "" +
                "select u.name as username, u.login as userlogin, d.site as usersite " +
                "from feedback.user u " +
                "inner join feedback.domain d on u.domain = d.id " +
                "where login = '" + map.get("login") + "' and password = '" + map.get("password") + "'";
        List<Object[]>  list = ses.createSQLQuery(sql)
                .addScalar("username", StandardBasicTypes.STRING)
                .addScalar("userlogin", StandardBasicTypes.STRING)
                .addScalar("usersite", StandardBasicTypes.STRING).list();
        ses.close();
        HashMap<String, Object> s = new HashMap<>();
        s.put("access", false);
        for (Object[] objects : list) {
            s.put("supportName", URLEncoder.encode(objects[0].toString()));
            String jwt = "";
            for (char c : objects[1].toString().toCharArray()) {
                int i = (int) c;
                jwt += ((int) c + 194) + "|";
                System.out.print(i);
                System.out.println((char) i);
            }
            s.put("jwt", jwt + "%" + objects[2]);
            s.put("access", true);
        }
        return s;
    }

    @Override
    public Object getEntityByName(String name) {
        try {
            Session s = sessionFactory.openSession();
            Class<?> d = Class.forName("entity." + "Entity" + name);
            List l = s.createCriteria(d).list();
            s.close();
            return l;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public Object getUserSession(String host, Cookie[] cookies) {
        EntityFeedBackSession feedBackSession = null;
        String cookie = getCookie(cookies, "FDBKAPI");
        if (cookie == null || cookie.equals("") || cookie.equals("undefined")) {
            Session ses = sessionFactory.openSession();
            feedBackSession = new EntityFeedBackSession();
            feedBackSession.setDomain_id(getIdByDomainName(host, ses));
            ses.saveOrUpdate(feedBackSession);
            ses.close();
        } else {
            feedBackSession = findByPrimaryKeyInt(EntityFeedBackSession.class, Integer.valueOf(cookie));
        }
        return feedBackSession;
    }

    @Override
    public Object getUserSessionById(Integer id) {
        Session s = sessionFactory.openSession();
        Criteria criteria = s.createCriteria(EntityFeedBackMessage.class);
        EntityFeedBackSession session = findByPrimaryKeyInt(EntityFeedBackSession.class, id);
        criteria.add(Restrictions.eq("session.id", id));
        List list = criteria.list();
        s.close();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("message", list);
        map.put("domain_id", session.getDomain_id());
        map.put("size", "в работе");
        map.put("page", "в работе");
        return map;
//        return findByPrimaryKeyInt(EntityFeedBackSession.class, id);
    }

    @Override
    public Object saveUserMessage(String message, String host, Cookie[] cookies, String user) {
        String cookie = getCookie(cookies, "FDBKAPI");
//        String domenId = getCookie(cookies, "FDBKD");
        Session ses = sessionFactory.openSession();
        EntityFeedBackMessage e = new EntityFeedBackMessage();
        e.setText(message);
        e.setDateCreate(new Date());
        e.setSession(findByPrimaryKeyInt(EntityFeedBackSession.class, Integer.valueOf(cookie)));
        e.setUser(user);
        ses.saveOrUpdate(e);
        ses.close();
        return e;
    }

    @Override
    public Object saveSupportMessage(HashMap map) {
        Session ses = sessionFactory.openSession();
        EntityFeedBackMessage e = new EntityFeedBackMessage();
        e.setText((String) map.get("text"));
        e.setDateCreate(new Date());
        e.setSession(findByPrimaryKeyInt(EntityFeedBackSession.class, (Integer) map.get("session_id")));
        e.setUser("support");
        ses.saveOrUpdate(e);
        ses.close();
        return map.get("session_id");
    }

    @Override
    public Object getDomainSessions(Integer id) {
        Session ses = sessionFactory.openSession();
        Criteria criteria = ses.createCriteria(EntityFeedBackSession.class);
        criteria.add(Restrictions.eq("domain_id", id));
        criteria.add(Restrictions.sizeGt("message", 0));
//        criteria.setFirstResult(pageNumber);
//        criteria.setMaxResults(pageSize);
        List l = criteria.list();
        for (Object o : l) {
            ((EntityFeedBackSession) o).setMessage(null);
        }
        ses.close();
        return l;
    }

    @Override
    public Object getMessageArray(Integer session, Integer lastId) {
        Session ses = sessionFactory.openSession();
        Criteria criteria = ses.createCriteria(EntityFeedBackMessage.class);
        criteria.add(Restrictions.eq("session", findByPrimaryKeyInt(EntityFeedBackSession.class, session)));
        criteria.add(Restrictions.ge("id", lastId + 1));
        List l = criteria.list();
        ses.close();
        return l;
    }

    //Возврат значение из куков
    @Override
    public String getCookie(Cookie[] cookies, String cookieName) {
        if (cookies == null) return "";
        String cookie = null;
        for (Cookie c : cookies) {
            if (c.getName().equals(cookieName) && c.getValue().length() > 0) {
                cookie = c.getValue();
                break;
            }
        }
        return cookie;
    }

    @Override
    public String getDecode(Cookie[] cookies) {
        String[] code = getCookie(cookies, "FDBKJWT").split("%");
        String login = "";
        if (code.length == 2) {
            String[] decode = code[0].replace("|", ",").split(",");
            for (String s : decode) {
                char c = (char) (Integer.parseInt(s) - 194);
                login += c;
            }

            System.out.println(code[0]);
        }
        return login;
    }

    // Ищем id домена
    private Integer getIdByDomainName(String domain, Session ses) {
        try {
            Criteria criteria = ses.createCriteria(EntityFeedBackDomain.class);
            if (domain != null) {
                criteria.add(Restrictions.eq("site", domain));
            }
            EntityFeedBackDomain tt = (EntityFeedBackDomain) criteria.uniqueResult();
            return tt.getId();
        } catch (NullPointerException e) {
            return createDomain(domain, ses);
        }
    }

    //Создаем домен, возвращаем id
    private Integer createDomain(String domain, Session ses) {
        EntityFeedBackDomain newDomain = new EntityFeedBackDomain();
        newDomain.setSite(domain);
        newDomain.setDateCreate(new Date());
        ses.saveOrUpdate(newDomain);
        return newDomain.getId();
    }

    /*строка из таблицы по ID*/
    private <T> T findByPrimaryKeyInt(Class<T> tClass, Integer primaryKey, String... fetchProfiles) {
        Session ses = sessionFactory.openSession();
        try {
            Criteria criteria = ses.createCriteria(tClass);
            if (primaryKey != null) {
                criteria.add(Restrictions.eq("id", primaryKey));
            }
            T tt = (T) criteria.uniqueResult();
            ses.close();
            return tt;
        } catch (NullPointerException e) {
            ses.close();
            return null;
        }
    }
}
