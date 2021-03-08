package com.sample.multitenant.mongo.filter;

import com.sample.multitenant.mongo.context.TenantContext;
import com.sample.multitenant.mongo.exception.TenantNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * User: rajeshgupta
 * Date: 08/03/21
 */
@AllArgsConstructor
@Slf4j
@Component
public class TenantFilter extends OncePerRequestFilter {

    private static final String TENANT_ID_HEADER = "X-Tenant";

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws IOException, ServletException {
        final String tenant = request.getHeader(TENANT_ID_HEADER);

        if (StringUtils.hasText(tenant)) {
            TenantContext.setTenantId(tenant);
        } else {
            throw new TenantNotFoundException("Tenant not found.");
        }
        filterChain.doFilter(request, response);
    }
}
