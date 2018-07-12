package groove.wind.me.web.intercepter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Set;

@Component
public class CommonIntercepter implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger("UserRecordLog");

//    @Autowired
//    private SysUserService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Integer currId = (Integer) request.getSession().getAttribute("DS:SEON:CURRID");

        /** 拦截已登录请求，未登录由shiro进行拦截 */
        if (currId != null && currId > 0) {
            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                return true;
            }

            String url = request.getRequestURI();
            if(url.indexOf("logout") != -1) {
                return true;
            }

            if (url.indexOf("/biz") != -1 || url.indexOf("/admin") != -1) {
                if (url.indexOf("/repair") != -1) {
                    return true;
                }
                /**
                Set<String> urls = sysUserService.findHasUrl(currId);
                for (String u : urls) {
                    if (StringUtils.isNotEmpty(u) && url.indexOf(u) != -1) {
                        return true;
                    }
                }
                 */
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        request.setAttribute("ctx", request.getContextPath());
        request.setAttribute("version", System.currentTimeMillis());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

    private String getParams(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder("{");
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            builder.append(param).append(":").append(request.getParameter(param)).append(",");
        }
        if (builder.length() > 1) {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append("}");
        return builder.toString();
    }

}
