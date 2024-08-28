package mono.focusider.domain.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.auth.dto.info.AuthUserInfo;
import mono.focusider.domain.auth.dto.req.CheckDuplicatedReqDto;
import mono.focusider.domain.auth.dto.req.LoginReqDto;
import mono.focusider.domain.auth.dto.req.SignupReqDto;
import mono.focusider.domain.auth.dto.res.CheckDuplicatedResDto;
import mono.focusider.domain.auth.helper.AuthHelper;
import mono.focusider.domain.auth.mapper.AuthMapper;
import mono.focusider.domain.auth.validator.AuthValidator;
import mono.focusider.domain.file.domain.File;
import mono.focusider.domain.file.helper.FileHelper;
import mono.focusider.domain.file.validate.FileValidate;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.helper.MemberHelper;
import mono.focusider.global.security.JwtUtil;
import mono.focusider.global.utils.cookie.CookieUtils;
import mono.focusider.global.utils.redis.RedisUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static mono.focusider.global.utils.cookie.CookieEnum.ACCESS_TOKEN;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthValidator authValidator;
    private final PasswordEncoder passwordEncoder;
    private final MemberHelper memberHelper;
    private final FileHelper fileHelper;
    private final FileValidate fileValidate;
    private final JwtUtil jwtUtil;
    private final AuthHelper authHelper;
    private final AuthMapper authMapper;
    private final RedisUtils redisUtils;

    @Transactional
    public void signup(SignupReqDto signupReqDto, HttpServletResponse response) {
        authValidator.checkAccountId(signupReqDto.accountId());
        String password = passwordEncoder.encode(signupReqDto.password());
        File profileImageFile = fileHelper.findFileByUrlOrNull(signupReqDto.profileImage());
        Member member = authHelper.createMemberAndSave(signupReqDto, profileImageFile, password);
        fileValidate.validateFileAndUpdateUsed(profileImageFile);
        AuthUserInfo authUserInfo = AuthUserInfo.of(member);
        setJwtToken(authUserInfo, response);
    }

    public void login(LoginReqDto loginReqDto, HttpServletResponse response) {
        Member member = memberHelper.findMemberByAccountIdOrThrow(loginReqDto.accountId());
        authValidator.validatePassword(loginReqDto.password(), member.getPassword(), passwordEncoder);
        AuthUserInfo authUserInfo = AuthUserInfo.of(member);
        setJwtToken(authUserInfo, response);
    }

    public CheckDuplicatedResDto checkDuplicated(CheckDuplicatedReqDto checkDuplicatedReqDto) {
        boolean check = authValidator.checkAccountIdWithBoolean(checkDuplicatedReqDto.accountId());
        return authMapper.toCheckDuplicatedResDto(check);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = CookieUtils.getCookieValueWithNameAndKill(request, response, ACCESS_TOKEN.getName());
        redisUtils.deleteData(accessToken);
    }

    private void setJwtToken(AuthUserInfo authUserInfo, HttpServletResponse response) {
        String accessToken = jwtUtil.createAccessToken(authUserInfo);
        String refreshToken = jwtUtil.createRefreshToken(authUserInfo);
        jwtUtil.addRedisTokenInfo(refreshToken, accessToken);
        jwtUtil.addAccessTokenToCookie(response, accessToken);
    }
}