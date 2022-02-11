package com.broanex.mes_log.aspect;

import com.broanex.mes_log.repository.MemberRepository;
import com.broanex.mes_log.util.TokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Aspect
@Component
public class AuthAop {
	private static final String AUTH = "accessToken";

	private final MemberRepository memberRepository;

	@Around("@annotation(com.broanex.mes_log.annotation.Auth)")
	public Object checkAccessToken(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object object;
		try {
			HttpServletRequest request =
					((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			String accessToken = request.getHeader(AUTH);
			TokenUtils tokenUtils = new TokenUtils();
			int id = tokenUtils.getId(accessToken);
			if (memberRepository.existsById(id)) {
				object = proceedingJoinPoint.proceed();
			} else {
				throw new SecurityException();
			}
		} catch (SignatureException | ExpiredJwtException | MalformedJwtException |
				UnsupportedJwtException | IllegalArgumentException e) {
			throw new JwtException("AccessToken Error");
		}
		return object;
	}
}
