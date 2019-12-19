package au.com.mebank.codingchallenge.joshluisaac.infrastructure.security;

import au.com.mebank.codingchallenge.joshluisaac.Routes;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers(
            "/vendors/**",
            "/js/**",
            "/images/**",
            "/api/v1/transactions/**",
            Routes.Transactions.ORDERS,
            Routes.Transactions.TRANSACTIONS)
        .permitAll()
        .anyRequest()
        .authenticated()
        // .and()
        // .formLogin()
        // .loginPage("/login")
        // .permitAll()
        .and()
        .logout()
        .permitAll();
  }

  /*    @Override
  public void configure(WebSecurity web) {
      super.configure(web);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      super.configure(auth);
  }*/
}
