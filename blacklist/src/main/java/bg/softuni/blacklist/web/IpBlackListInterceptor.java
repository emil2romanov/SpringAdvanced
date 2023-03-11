package bg.softuni.blacklist.web;

import bg.softuni.blacklist.service.BlackListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class IpBlackListInterceptor implements HandlerInterceptor {

    private BlackListService service;
    private ThymeleafViewResolver tlvr;

    public IpBlackListInterceptor(BlackListService service, ThymeleafViewResolver tlvr) {
        this.service = service;
        this.tlvr = tlvr;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //return HandlerInterceptor.super.preHandle(request, response, handler);
        String ip = getIpAddressFromRequest(request);

        if (service.isBlackListed(ip)) {
            View blockedView = tlvr.resolveViewName("blocked", Locale.getDefault());
            if (blockedView != null) {
                blockedView.render(Map.of(), request, response);
            }
            return false;
        }

        return true;
    }

    private String getIpAddressFromRequest(HttpServletRequest request) {

        String ipAddress = null;

        String xffHeader = request.getHeader("X-Forwarded-For");
        if (xffHeader != null && !xffHeader.equals("unknown")) {
            int commaIdx = xffHeader.indexOf(",");
            if (commaIdx > 0) {
                ipAddress = xffHeader.substring(0, commaIdx - 1);
            }
        }

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }
}
