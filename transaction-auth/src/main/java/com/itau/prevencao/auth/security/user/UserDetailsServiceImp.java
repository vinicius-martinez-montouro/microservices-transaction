package com.itau.prevencao.auth.security.user;

import com.itau.security.model.ApplicationUser;
import com.itau.security.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author vinicius.montouro
 */
@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImp implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info(String.format("Pesquisar no banco de dados o usuário: '%s'", username));
        ApplicationUser applicationUser = applicationUserRepository.findByUserName(username);
        log.info(String.format("Usuário '%s' encontrado", username));
        if(applicationUser == null)
            throw new UsernameNotFoundException("Usuário '%s' não encontrado no banco de dados");
        return new CustomUserDetails(applicationUser);
    }

    private static final class CustomUserDetails extends ApplicationUser implements UserDetails{

        public CustomUserDetails(@NotNull ApplicationUser applicationUser){
            super(applicationUser);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_");
        }

        @Override
        public String getPassword() {
            return getPassword();
        }

        @Override
        public String getUsername() {
            return getName();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
