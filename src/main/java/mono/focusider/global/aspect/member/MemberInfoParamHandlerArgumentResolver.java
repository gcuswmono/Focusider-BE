package mono.focusider.global.aspect.member;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mono.focusider.global.annotation.MemberInfo;
import mono.focusider.global.error.code.GlobalErrorCode;
import mono.focusider.global.error.exception.UnauthorizedException;
import mono.focusider.global.security.JwtEnum;
import mono.focusider.global.security.JwtUtil;
import mono.focusider.global.utils.cookie.CookieUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static mono.focusider.global.security.JwtEnum.*;

@RequiredArgsConstructor
@Component
public class MemberInfoParamHandlerArgumentResolver implements HandlerMethodArgumentResolver {
    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberInfoParam.class)
                && parameter.hasParameterAnnotation(MemberInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String accessToken = CookieUtils.getCookieValueWithName(request, ACCESS_TOKEN_NAME.getDesc());
        if(!StringUtils.hasText(accessToken)){
            throw new UnauthorizedException(GlobalErrorCode.INVALID_TOKEN);
        }
        Long memberId = jwtUtil.getMemberId(accessToken);
        String memberRole = jwtUtil.getMemberRole(accessToken);
        return MemberInfoParam.of(memberId, memberRole);
    }
}
