package com.broanex.mes_log.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

public class TokenUtils {

	private long ACCESS_TOKEN_VALID_SECOND = 60 * 60 * 2;

	private long REFRESH_TOKEN_VALID_SECOND = 60 * 60 * 24 * 14;

	private String secretKey = "%Z&Dif!k5<L)Du?&6{YDO}0t*Z*Wu;etmTFG_2U??SfW(k!_afX5{qtC8[d&?5=";

	private Key getSignInKey() {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public Claims validTokenAndReturnBody(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(getSignInKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException | UnsupportedJwtException |
				MalformedJwtException | SignatureException | IllegalArgumentException e) {
			e.printStackTrace();
			throw new InvalidParameterException("유효하지 않은 토큰입니다.");
		}
	}

	public int getId(String token) {
		return validTokenAndReturnBody(token).get("id", Integer.class);
	}

	public Boolean isTokenExpired(String token) {
		Date expiration = validTokenAndReturnBody(token).getExpiration();
		return expiration.before(new Date());
	}

	public String generateAccessToken(int id) {
		return doGenerateToken(id, ACCESS_TOKEN_VALID_SECOND * 1000L);
	}

	private String generateRefreshToken(int id) {
		return doGenerateToken(id, REFRESH_TOKEN_VALID_SECOND * 1000L);
	}

	private String doGenerateToken(int id, Long expireTime) {
		Claims claims = Jwts.claims();
		claims.put("id", id);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expireTime))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public HashMap<String, String> generateAccessTokenAndRefreshToken(int id) {
		return new HashMap<String, String>() {{
			put("accessToken", generateAccessToken(id));
			put("refreshToken", generateRefreshToken(id));
		}};
	}

}
