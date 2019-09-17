package com.vd.mall.admin.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import vd.mall.response.RestResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by pengq on 2019/9/17 8:57.
 */
public class WriteResponse {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void write(HttpServletResponse httpServletResponse, RestResponse restResponse) throws IOException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter out = httpServletResponse.getWriter();
        out.write(mapper.writeValueAsString(restResponse));
        out.flush();
        out.close();
    }
}
