package org.example.api.web.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class ServletUtil {


    public static void writeJson(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(json.getBytes());
        response.flushBuffer();
    }


}
