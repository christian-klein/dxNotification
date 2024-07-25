package com.hcl.dxUserProfile.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class LtpaTokenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String ltpaToken = getLtpaTokenFromCookies(request.getCookies());

		// request.getHeader("Authorization");

		if (ltpaToken != null) {
			try {
				// Validate LTPA token using WebSphere or any custom validation logic
				boolean isValidToken = validateLtpaToken(ltpaToken);

				if (isValidToken) {
					User user = new User("authenticatedUser", "", new ArrayList<>());
					PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(user,
							"", user.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid LTPA Token");
					return;
				}
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid LTPA Token");
				return;
			}
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Header Required");
			return;
		}

		filterChain.doFilter(request, response);
	}

	private String getLtpaTokenFromCookies(Cookie[] cookies) {
		System.out.println("Extracting LtpaToken2 cookie");
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("LtpaToken2".equals(cookie.getName())) {
					System.out.println("Getting a valid cookie, the value is " + cookie.getValue());
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	private boolean validateLtpaToken(String ltpaToken) {
		// Implement the LTPA token validation logic here
		// This might involve using WebSphere APIs or custom logic to validate the token
		if (ltpaToken != null) {
			return true; // Replace with actual validation logic
		}

		return false;
	}

}