package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.viewFormService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Created by Nurzhan on 31.10.2017.
 */
@RestController
@ComponentScan({"services", "servicesImpl"})
@RequestMapping(value = "/view")
public class viewFormController {
    @Autowired()
    private viewFormService viewFormServices;

    private String BACKGROUND_COLOR = " background-color:";
    private String WIDTH = " width:";
    private String HEIGHT = " height:";


    @RequestMapping(value = "/form-1", produces = {"application/json; charset=UTF-8"})
    public Object getForm1(HttpServletRequest request) {
        String form = "";
        if (viewFormServices.getAccessDomain(request.getServerName())) {
            form += getHTML("div",
                    WIDTH + "250px;" + BACKGROUND_COLOR + "blue; padding: 5px; background: #565b6d; border-top-right-radius: 25px",
                    "",
                    getHTML("a",
                            "font-size: 13px; font-family: Arial, Arial; font-weight: bold; color: rgb(240, 241, 241);",
                            "",
                            "Отправьте нам сообщение")
            ) +
                    getHTML(
                            "div",
                            WIDTH + "230px;" + BACKGROUND_COLOR + "rgb(55, 58, 73);" +
                                    "padding: 15px; border-bottom-left-radius: 25px;",
                            "",
                            getHTML("div",
                                    "width: 100%; height: 200px; background: white; margin-bottom: 10px; overflow: hidden;",
                                    "",
                                    getHTML("div",
                                            "border: 1px solid red; margin-bottom: 10px; overflow: auto; height: 200px;",
                                            "id='FeedBackMessage'",
                                            "")
                            ) +
                                    getHTML("textarea",
                                            "resize: none; width: 100%; ",
                                            "rows='2' id='FeedBackCreateMessage'",
                                            "") +
                                    getHTML("div",
                                            "margin-top: 12px;",
                                            "align='right'",
                                            getHTML("a",
                                                    "font-weight: 700; color: white; " +
                                                            "text-decoration: none; padding: .4em 1em calc(.3em + 3px); " +
                                                            "border-radius: 3px; background: rgb(64,199,129);",
                                                    "onclick='saveMessage()'",
                                                    "Отправить")
                                    )

                    );
        }

        return form;
    }

    private String getHTML(String component, String style, String function, String content) {
        return "<" + component + " " + function + " style=\"" + style + "\">" + content + "</" + component + ">";
    }
}
