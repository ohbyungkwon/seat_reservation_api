package com.seat.reservation.common.security.oauth;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.security.CustomAuthenticationException;
import com.seat.reservation.common.security.MyUserDetails;
import com.seat.reservation.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2ClientUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User auth2User = super.loadUser(userRequest);
        User user = getOurServiceUser(userRequest, auth2User);
        return new MyUserDetails(user);
    }

    private User getOurServiceUser(OAuth2UserRequest userRequest, OAuth2User auth2User) {
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = auth2User.getAttributes();
        OAuth2UserInfo oauth2UserInfo = RelatedOAuth2Factory.getOauthUserInfo(provider, attributes);

        String email = oauth2UserInfo.getEmail();
        if(StringUtils.isEmpty(email)){
            throw new CustomAuthenticationException(provider + "에 존재하지 않는 정보입니다.");
        }

        log.info("=========================================");
        log.info("Do OAuth2 Login[provider : {}]", provider);
        log.info("Do OAuth2 Login[email : {}]", email);
        log.info("Do OAuth2 Login[date : {}]", CommonUtil.getNowDate());

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomAuthenticationException("존재하지 않는 회원입니다."));
    }
}
