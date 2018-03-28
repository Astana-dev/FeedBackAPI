package project.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nurzhan on 01.11.2017.
 */
@RestController
public class jsController {
    @RequestMapping(value = "feedback/{name}", produces = {"application/json; charset=UTF-8"})
    public String getForm2(@PathVariable("name") String name) {
        String js = "window.onload = function () {\n" +
                "    var d = document.createElement('fdiv');\n" +
                "    d.style.position = \"fixed\";\n" +
                "    d.style.zIndex = \"999999\";\n" +
                "    d.style.bottom = \"20px\";\n" +
                "    d.style.right = \"20px\";\n" +
                "    d.id = \"getFeedBack\";\n" +
                "    document.body.appendChild(d);\n" +
                "\n" +
                "    $.get('http://localhost/view/" + name + "', function (data) {\n" +
                "        document.getElementById(\"getFeedBack\").innerHTML = data.test;\n" +
                "    });\n" +
                "}";
        return js;
    }
}
