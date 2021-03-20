package com.example.demo.security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final static Logger logger= LoggerFactory.getLogger(WebSecurityConfiguration.class);

    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfiguration(UserDetailsServiceImpl userDetailsService,
                                    BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        try {
            http.cors().and().csrf().disable().authorizeRequests()
                    .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                    .addFilter(new JWTAuthenticationVerficationFilter(authenticationManager()))
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

       }catch(Exception e){
            logger.error("*************************************************");
            logger.error("*************************************************");
            logger.error(e.getMessage());
            logger.error("*************************************************");
            logger.error("*************************************************");
            throw (e);
        }
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
       try {
            return super.authenticationManagerBean();

        }catch(Exception exp){

            logger.error("*************************************************");
            logger.error("*************************************************");
            logger.error(exp.getMessage());
            logger.error("*************************************************");
            logger.error("*************************************************");
            throw(exp);

        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        try {
            auth.parentAuthenticationManager(authenticationManagerBean())
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(bCryptPasswordEncoder);

        }catch(Exception exp){

            logger.error("*************************************************");
            logger.error("*************************************************");
            logger.error(exp.getMessage());
            logger.error("*************************************************");
            logger.error("*************************************************");
            throw(exp);

        }
    }
}