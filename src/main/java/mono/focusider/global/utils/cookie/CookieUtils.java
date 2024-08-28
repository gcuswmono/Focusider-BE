package mono.focusider.global.utils.cookie;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public class CookieUtils {
    public static Cookie createCookie(String name, String value) {
        return new Cookie(name, value);
    }

    public static Cookie[] getCookies(HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies()).orElse(new Cookie[0]);
    }

    public static String getCookieValueWithName(HttpServletRequest request, String name) {
        Cookie[] cookies = getCookies(request);
        try{
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String getCookieValueWithNameAndKill(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = getCookies(request);
        try{
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    String cookieValue = cookie.getValue();
                    killCookie(cookie, response);
                    return cookieValue;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void killCookie(Cookie cookie, HttpServletResponse response) {
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setDomain("focusider.shop");
        //cookie.setAttribute("SameSite", "None");
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    public static void addCookie(HttpServletResponse response, Cookie cookie) {
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static void addCookieWithMaxAge(HttpServletResponse response, Cookie cookie, int maxAge) {
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setDomain("focusider.shop");
        //cookie.setAttribute("SameSite", "None");
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}
