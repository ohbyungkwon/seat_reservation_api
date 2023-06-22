package com.seat.reservation.common.service;

import com.seat.reservation.common.cache.CustomRedisCacheWriter;
import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.dto.UserDto;
import com.seat.reservation.common.exception.NotFoundPrincipalException;
import com.seat.reservation.common.repository.MerchantRepository;
import com.seat.reservation.common.repository.UserRepository;
import com.seat.reservation.common.security.AuthConstants;
import com.seat.reservation.common.security.TokenUtils;
import com.seat.reservation.common.support.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

public class SecurityService {

    public Optional<User> getUser() throws IOException {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader(AuthConstants.AUTH_HEADER);
        UserDto.create userDto = TokenUtils.getUser(token);

        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        return userRepository.findByUserId(userDto.getUserId());
    }

    /**
     * SESSION 사용할 경우 정상 동작
     */
    protected Optional<User> getUserInSession(){
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw new NotFoundPrincipalException("Principal 만료");
        }
        UserDto.create userDto = (UserDto.create) authentication.getPrincipal();

        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        return userRepository.findByUserId(userDto.getUserId());
    }
    
    // 확인 필요
    protected Optional<Object> getMerchant() {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();

        MerchantRepository merchantRepository = applicationContext.getBean(MerchantRepository.class);

        Principal principal = (Principal) Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .orElseThrow(() -> new NotFoundPrincipalException("Principal 만료"));

        // 이게 맞는건가?
        return Optional.ofNullable(merchantRepository.findByMerchantRegNum(Integer.parseInt(principal.getName())));
    }
}
