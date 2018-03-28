package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import services.dataService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Created by Nurzhan on 11.10.2017.
 */
@RestController
@ComponentScan({"services", "servicesImpl"})
@RequestMapping(value = "/data")
public class dataController {
    @Autowired()
    private dataService dataServices;

    // get Entity
    @RequestMapping(value = "/entity/{name}", produces = {"application/json; charset=UTF-8"})
    public Object getEntity(@PathVariable("name") String name) {
        return dataServices.getEntityByName(name);
    }

/* ///////////////////////////////////////////////////////////////////////////////////////////////////// */
/* ///////////////////////////////////////////////////////////////////////////////////////////////////// */
    /*Гость*/
    // Сессия
    @RequestMapping(value = "/session", produces = {"application/json; charset=UTF-8"})
    public Object getSessionUser(HttpServletRequest request) {
        return dataServices.getUserSession(request.getServerName(), request.getCookies());
    }
    //Новые сообщения
    @RequestMapping(value = "/message/{lastId}", produces = {"application/json; charset=UTF-8"})
    public Object getMessage(HttpServletRequest request, @PathVariable("lastId") Integer lastId) {
        Integer session = Integer.valueOf(dataServices.getCookie(request.getCookies(), "FDBKAPI"));
        return dataServices.getMessageArray(session, lastId);
    }
    //Сохранить сообщение от пользователя
    @RequestMapping(value = "/save", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
    public Object saveMessage(@RequestParam("user") String user,
                              @RequestParam("message") String message,
                              HttpServletRequest request) {
        try {
            message = new String(message.getBytes("iso-8859-1"), "UTF-8");
            return dataServices.saveUserMessage(message, request.getServerName(), request.getCookies(), user);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

/* ///////////////////////////////////////////////////////////////////////////////////////////////////// */
/* ///////////////////////////////////////////////////////////////////////////////////////////////////// */
    /*SUPPORT*/
    @RequestMapping(value = "/sessions", produces = {"application/json; charset=UTF-8"})
    public Object sessions(HttpServletRequest request) {
        return dataServices.getDomainSessions(2);
    }

    //Сохранить сообщение от SUPPORT
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public Object saveMessageSupport(HttpServletRequest request, @RequestBody HashMap map) {
        return dataServices.saveSupportMessage(map);
    }

    // Сессия по id
    @RequestMapping(value = "/session/{id}", produces = {"application/json; charset=UTF-8"})
    public Object getSessionUserById(@PathVariable("id") Integer id) {
        return dataServices.getUserSessionById(id);
    }
/* ///////////////////////////////////////////////////////////////////////////////////////////////////// */
/* ///////////////////////////////////////////////////////////////////////////////////////////////////// */

    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
    public Object authentification(@RequestBody HashMap map) {
        return dataServices.verification(map);
    }
}
