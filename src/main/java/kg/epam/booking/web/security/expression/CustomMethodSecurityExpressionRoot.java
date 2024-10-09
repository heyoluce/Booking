package kg.epam.booking.web.security.expression;


import jakarta.servlet.http.HttpServletRequest;
import kg.epam.booking.domain.enums.Role;
import kg.epam.booking.web.security.JwtEntity;
import kg.epam.booking.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Setter
@Getter
public class CustomMethodSecurityExpressionRoot
        extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;
    private HttpServletRequest request;

    private UserService userService;

    public CustomMethodSecurityExpressionRoot(
            final Authentication authentication
    ) {
        super(authentication);
    }

    public boolean canAccessUser(
            final Long id
    ) {
        JwtEntity user = (JwtEntity) this.getPrincipal();
        Long userId = user.getId();

        return userId.equals(id) || hasAnyRole(Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(
            final Role... roles
    ) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        for (Role role : roles) {
            SimpleGrantedAuthority authority
                    = new SimpleGrantedAuthority(role.name());
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Object getThis() {
        return target;
    }

}
