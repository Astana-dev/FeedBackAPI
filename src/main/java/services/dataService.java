package services;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.HashMap;

/**
 * Created by Nurzhan on 11.10.2017.
 */
@Service
public interface dataService {
    Object getEntityByName(String name);
    Object getUserSession(String host, Cookie[] cookies);
    Object saveUserMessage(String message, String host, Cookie[] cookies, String user);
    Object saveSupportMessage(HashMap map);

    Object getDomainSessions(Integer id);
    Object getUserSessionById(Integer id);


    Object getMessageArray(Integer session, Integer lastId);



    String getCookie(Cookie[] cookies, String cookieName);
    String getDecode(Cookie[] cookies);

    Object verification(HashMap map);
}
