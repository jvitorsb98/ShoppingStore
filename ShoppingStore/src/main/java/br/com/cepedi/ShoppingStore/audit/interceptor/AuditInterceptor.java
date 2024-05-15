package br.com.cepedi.ShoppingStore.audit.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.security.core.Authentication;
import br.com.cepedi.ShoppingStore.audit.service.AuditService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuditInterceptor implements HandlerInterceptor {

    @Autowired
    private AuditService auditService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Captura o endereço IP de origem
        String origin = request.getHeader("X-FORWARDED-FOR");
        if (origin == null) {
            origin = request.getRemoteAddr();
        }

        // Recupera informações do usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "unauthenticated user";
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            userId = user.getUsername();
        }

        String eventName = "Request to " + request.getRequestURI();
        String description = "Method: " + request.getMethod();

        // Loga o evento
        auditService.logEvent(eventName, description, userId, request.getRequestURI(), origin);
        return true;
    }

    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}
}
