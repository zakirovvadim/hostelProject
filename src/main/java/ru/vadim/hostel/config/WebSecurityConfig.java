package ru.vadim.hostel.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static ru.vadim.hostel.config.ApplicationUserPermission.*;
import static ru.vadim.hostel.config.ApplicationUserPermission.CATEGORY_UPDATE;
import static ru.vadim.hostel.config.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)//необходи после высталвения аннотаций @PreAuthorize
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .usersByUsernameQuery("select name, password, active from usr where name=?")
//                .authoritiesByUsernameQuery("select u.name, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.name=?");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails annasmith = User.builder()
                .username("annasmith")
                .password(passwordEncoder.encode("password"))
                //.roles(MANAGER.name())
                .authorities(MANAGER.getSimpleGrantedAuthorities())
                .build();


        UserDetails linda = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("password123"))
                //.roles(ADMIN.name())
                .authorities(ADMIN.getSimpleGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(
                annasmith,
                linda
        );
    }

//    oauth
//    @Bean
//    public PrincipalExtractor principalExtractor(UserDetailsRepository userDetailsRepo) {
//        return map -> {
//            String id = (String) map.get("sub");
//            User user = userDetailsRepo.findById(id).orElseGet(() -> {
//                User newUser = new User();
//                newUser.setId(id);
//                newUser.setName((String) map.get("name"));
//                newUser.setEmail((String) map.get("email"));
//                newUser.setRole(Role.MANAGER);
//                return newUser;
//            });
//
//            return userDetailsRepo.save(user);
//        };
//    }

}
