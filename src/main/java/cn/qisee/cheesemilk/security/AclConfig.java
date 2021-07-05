package cn.qisee.cheesemilk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
public class AclConfig {

    @Autowired
    DataSource dataSource;

    @Autowired
    CacheManager cacheManager;

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
    }

    @Bean
    public PermissionGrantingStrategy grantingStrategy() {
        return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
    }

    @Bean
    public SpringCacheBasedAclCache aclCache(CacheManager cacheManager,
                                             PermissionGrantingStrategy permissionGrantingStrategy,
                                             AclAuthorizationStrategy aclAuthorizationStrategy) {
        Cache cache = Optional.ofNullable(cacheManager.getCache("acl")).orElse(new ConcurrentMapCache("acl"));

        return new SpringCacheBasedAclCache(cache, permissionGrantingStrategy, aclAuthorizationStrategy);
    }

    @Bean
    public BasicLookupStrategy lookupStrategy(DataSource dataSource,
                                              AclCache cache,
                                              AclAuthorizationStrategy aclAuthorizationStrategy,
                                              PermissionGrantingStrategy permissionGrantingStrategy) {
        return new BasicLookupStrategy(dataSource, cache, aclAuthorizationStrategy, permissionGrantingStrategy);
    }

    @Bean
    public JdbcMutableAclService aclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache) {
        JdbcMutableAclService aclService = new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
        aclService.setClassIdentityQuery("SELECT @@IDENTITY");
        aclService.setSidIdentityQuery("SELECT @@IDENTITY");
        return aclService;
    }

}
