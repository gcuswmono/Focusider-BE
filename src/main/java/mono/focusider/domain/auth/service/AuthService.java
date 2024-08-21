package mono.focusider.domain.auth.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.auth.dto.info.AuthUserInfo;
import mono.focusider.domain.auth.dto.req.LoginRequestDto;
import mono.focusider.domain.auth.dto.req.SignupRequestDto;
import mono.focusider.domain.auth.helper.AuthHelper;
import mono.focusider.domain.auth.validator.AuthValidator;
import mono.focusider.domain.file.domain.File;
import mono.focusider.domain.file.helper.FileHelper;
import mono.focusider.domain.member.domain.Member;
import mono.focusider.domain.member.helper.MemberHelper;
import mono.focusider.global.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final AuthValidator authValidator;
    private final PasswordEncoder passwordEncoder;
    private final MemberHelper memberHelper;
    private final FileHelper fileHelper;
    private final JwtUtil jwtUtil;
    private final AuthHelper authHelper;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto) {
        authValidator.checkAccountId(signupRequestDto.accountId());
        String password = passwordEncoder.encode(signupRequestDto.password());
        File profileImageFile = fileHelper.findFileByUrlOrNull(signupRequestDto.profileImage());
        authHelper.createMemberAndSave(signupRequestDto, profileImageFile, password);
        fileHelper.checkFileIsPresent(profileImageFile);
    }

    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        Member member = memberHelper.findMemberByAccountIdOrThrow(loginRequestDto.accountId());
        authValidator.validatePassword(loginRequestDto.password(), member.getPassword(), passwordEncoder);
        AuthUserInfo authUserInfo = AuthUserInfo.of(member);
        String accessToken = jwtUtil.createAccessToken(authUserInfo);
        String refreshToken = jwtUtil.createRefreshToken(authUserInfo);
        jwtUtil.addRedisTokenInfo(refreshToken, accessToken);
        jwtUtil.addAccessTokenToCookie(response, accessToken);
    }
}