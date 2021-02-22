package beer.cheese.security.jwt;

import beer.cheese.model.ManagerGroup;
import beer.cheese.model.Role;
import beer.cheese.model.User;
import beer.cheese.repository.UserRepository;
import beer.cheese.util.JwtUtils;
import io.jsonwebtoken.JwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.NoOpCache;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    /**
     * The plaintext password used to perform
     * PasswordEncoder#matches(CharSequence, String)}  on when the user is
     * not found to avoid SEC-2056.
     */
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
    private final Log logger = LogFactory.getLog(getClass());

    // ~ Instance fields
    // ================================================================================================
    private PasswordEncoder passwordEncoder;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();


    private Cache userCache = new NoOpCache("NoOp");

    /**
     * The password used to perform
     * {@link PasswordEncoder#matches(CharSequence, String)} on when the user is
     * not found to avoid SEC-2056. This is necessary, because some
     * {@link PasswordEncoder} implementations will short circuit if the password is not
     * in a valid format.
     */
    private volatile String userNotFoundEncodedPassword;

    private UserRepository userRepository;

    public JwtAuthenticationProvider() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(JwtAuthenticationToken.class, authentication,
                "Only UsernamePasswordAuthenticationToken is supported");

        String username;
        try {
            String jwtToken = (authentication.getPrincipal() == null) ? "NONE_PROVIDED"
                    : authentication.getName();
            username = JwtUtils.parseToken(jwtToken);
        }catch (JwtException e){
           throw new BadCredentialsException("jwt token authenticate failure", e);
        }


        boolean cacheWasUsed = true;
        Object user = this.userCache.get(username, User.class);
        if(user != null){
            logger.error("have cache");
        }else
            logger.error("no cache");
        if (user == null) {
            cacheWasUsed = false;

            try {
                user = retrieveUser(username);
//                logger.error(((User)user).getAvatarUrl());
            } catch (UsernameNotFoundException notFound) {
//                logger.debug("User '" + username + "' not found");
                throw notFound;
            }

            Assert.notNull(user,
                    "retrieveUser returned null - a violation of the interface contract");
        }

        if (!cacheWasUsed) {
            this.userCache.put(username, user);
        }

        Object principalToReturn = user;

        return createSuccessAuthentication(principalToReturn, authentication);
    }

    protected Authentication createSuccessAuthentication(Object principal,
                                                         Authentication authentication) {

        Set<Role> roles = ((User)principal).getRoles();
        Set<ManagerGroup> managerGroups = ((User)principal).getManagerGroups();
        List<GrantedAuthority> roleWrappers = roles.stream()
                .map( (role) -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());
        List<GrantedAuthority> groupWrappers = managerGroups.stream()
                .map( (group) -> new SimpleGrantedAuthority("ROLE_" + group.getCategory().getCategoryName()))
                .collect(Collectors.toList());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(roleWrappers);
        authorities.addAll(groupWrappers);

        JwtAuthenticationToken result = new JwtAuthenticationToken(principal, authorities);

        result.setDetails(authentication.getDetails());

        return result;
    }

    protected User retrieveUser(String username) throws AuthenticationException {
        try {
            User loadedUser = userRepository.findByUsername(username).orElseThrow(() ->
                    new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation")
            );
            return loadedUser;
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }


    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public GrantedAuthoritiesMapper getAuthoritiesMapper() {
        return authoritiesMapper;
    }

    public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
        this.authoritiesMapper = authoritiesMapper;
    }

    public Cache getUserCache() {
        return userCache;
    }

    public void setUserCache(Cache userCache) {
        this.userCache = userCache;
    }

    public String getUserNotFoundEncodedPassword() {
        return userNotFoundEncodedPassword;
    }

    public void setUserNotFoundEncodedPassword(String userNotFoundEncodedPassword) {
        this.userNotFoundEncodedPassword = userNotFoundEncodedPassword;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
