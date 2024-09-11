package com.seat.reservation.common.service.impl;

import com.seat.reservation.common.cache.CustomRedisCache;
import com.seat.reservation.common.domain.RefreshTokenStore;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.domain.enums.CacheName;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.exception.BadReqException;
import com.seat.reservation.common.exception.NotFoundUserException;
import com.seat.reservation.common.repository.RefreshTokenStoreRepository;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.security.AuthConstants;
import com.seat.reservation.common.security.TokenUtils;
import com.seat.reservation.common.service.SecurityService;
import com.seat.reservation.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends SecurityService implements UserService {
    private final UserRepository userRepository;

    private final RefreshTokenStoreRepository refreshTokenStoreRepository;

    private final Map<String, CustomRedisCache> redisCacheMap;

    private final PasswordEncoder passwordEncoder;


    /**
     * @param dto
     * @return UserDto.create
     * @throws Exception
     */
    @Override
    @Transactional
    public UserDto.search createUser(UserDto.create dto) throws Exception {
        Optional<User> user = userRepository.findByUserId(dto.getUserId());
        if(user.isPresent()){
            throw new BadReqException("이미 가입된 사용자입니다.");
        }

        return userRepository
                .save(User.createUser(dto, passwordEncoder))
                .convertDto();
    }

    /**
     * @param dto
     * @throws Exception
     */
    @Override
    @Transactional
    public void updateUser(UserDto.update dto) throws Exception {
        User user = this.getUser()
                .orElseThrow(() -> new NotFoundUserException("사용자를 찾지 못했습니다."));
        user.updateUser(dto, passwordEncoder);
    }

    /**
     * @param request
     * @param response
     * @throws Exception
     * - 토큰 갱신
     * - AccessToken: Send a token to the response's header.
     * - RefreshToken: Store at the DB and Send a key to a client to find data.
     */
    @Override
    @Transactional
    public void manageRefreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 기존 refresh-token's cookie value 가져오기
        Optional<Cookie> cookieOptional = Arrays.stream(request.getCookies())
                .filter(x -> x.getName().equals(AuthConstants.getRefreshTokenKey())).findFirst();
        if(!cookieOptional.isPresent()) {
            throw new BadReqException("토큰 정보가 전달되지 않았습니다.");
        }

        // 기존 refresh-token 검증
        Cookie cookie = cookieOptional.get();
        String cookieValue = cookie.getValue();
        RefreshTokenStore refreshTokenStore =
                refreshTokenStoreRepository.findByCookieValue(cookieValue)
                        .orElseThrow(() -> new BadReqException("토큰이 존재하지 않습니다."));
        String oldRefreshToken = refreshTokenStore.getRefreshToken();
        boolean isValid = TokenUtils.isValidToken(oldRefreshToken, "");
        if(!isValid) throw new BadReqException("토큰 정보가 불일치합니다.");

        // refresh-token, cookie value 변경
        refreshTokenStore.renewRefreshTokenStore();
        cookie.setValue(refreshTokenStore.getCookieValue());
        cookie.setPath("/");
        response.addCookie(cookie);

        // access-token 재발급
        String userId = refreshTokenStore.getUserId();
        UserDto.search user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BadReqException("존재하지 않는 사용자 입니다."))
                .convertDto();

        String accessToken = TokenUtils.generateJwtToken(user);
        String redisTokenKey = AuthConstants.getAccessTokenKey(userId);
        redisCacheMap.get(CacheName.TOKEN_CACHE.getValue()).put(redisTokenKey, accessToken);
        response.addHeader(AuthConstants.AUTH_HEADER,
                AuthConstants.ACCESS_TOKEN_TYPE + " " + accessToken);
    }


    public UserDto.search getMyInfo() {
        return this.getUserInfo();
    }
}
