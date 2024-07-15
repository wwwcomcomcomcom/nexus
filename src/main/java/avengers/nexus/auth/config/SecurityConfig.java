package avengers.nexus.auth.config;

import avengers.nexus.auth.filter.GauthAuthenticationFilter;
import avengers.nexus.auth.filter.GithubAuthenticationFilter;
import avengers.nexus.auth.filter.JwtAuthorizationFilter;
import avengers.nexus.auth.jwt.JWTUtil;
import avengers.nexus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public GauthAuthenticationFilter gauthAuthenticationFilter(AuthenticationManager authenticationManager){
        GauthAuthenticationFilter filter = new GauthAuthenticationFilter("/user/login/gauth", authenticationManager, jwtUtil, userService);
        filter.setFilterProcessesUrl("/user/login/gauth");
        return filter;
    }
    @Bean
    public GithubAuthenticationFilter githubAuthenticationFilter(AuthenticationManager authenticationManager){
        GithubAuthenticationFilter filter = new GithubAuthenticationFilter("/user/login/github", authenticationManager, jwtUtil, userService);
        filter.setFilterProcessesUrl("/user/login/github");
        return filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests)->
                        authorizeRequests
                                .requestMatchers("/user/**").permitAll()
                                .requestMatchers("/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/project/**","/post/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(gauthAuthenticationFilter(http.getSharedObject(AuthenticationManager.class)), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil,userService), GauthAuthenticationFilter.class)
                .addFilterAfter(githubAuthenticationFilter(http.getSharedObject(AuthenticationManager.class)), GauthAuthenticationFilter.class)
        ;
        return http.build();
    }
}
