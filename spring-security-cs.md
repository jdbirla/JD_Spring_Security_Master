# Spring-security-cs

## Core Concept in spring security
### Authentication :  Who are you , can you prove that's you , Who is this user?
   - #### Knowledge based authenticaion : 
   		- Password
   		- Pin code
   		- Answer to a secret / personal question
   - #### Possession based authenticaion : 
   		- Phone/ text Message
   		- Key Cards and badges
   		- Access token device
### Authorization : Can this user do this / Are they allowed to do this?

### Principal : Currently loggern in user

### Granted Authority : What they can do

### Roles : Group of Authorities ROLE_ADMIN

---

# Basic or Default Spring Security 
 - Adding just `spring-boot-starter-security`
 - Adds mandatory authentication for URLs
 - Add login form
 - Handles login error
 - Create a user and sets a default password
 - We can configure user and password in properties files

## How Spring security configuration 
### Authentication
 - Created spring security configuration using extending WebSecurityConfigurerAdapter and overried configure method
 - AuthenticationManger provides the configure methos using AuthenticationMangerBuilder `protected void configure(AuthenticationManagerBuilder auth) throws Exception`
### Authorization
 - In-memory authentication configuration
 - AuthenticationManger provides the configure methos using HttpSecurity `protected void configure(HttpSecurity http) throws Exception `
 - ```  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .and().formLogin();
    }
    ```
## How Spring security works
- Spring security add spring security filter before application and it intercept each request
- ![image](https://user-images.githubusercontent.com/69948118/219938339-ba454d6d-25f3-410b-810e-de6884e4532a.png)
-  AuthenticationProvider Interface has authenticate() method which takes Authentication object as arguement and return Authentication object

	```java
	public interface AuthenticationProvider {
	    Authentication authenticate(Authentication authentication) throws AuthenticationException;

	    boolean supports(Class<?> authentication);
	}
	```
  - Authentication interface hasall details about user 
     ```java
	     public interface Authentication extends Principal, Serializable {
	    Collection<? extends GrantedAuthority> getAuthorities();

	    Object getCredentials();

	    Object getDetails();

	    Object getPrincipal();

	    boolean isAuthenticated();

	    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;
	}
```
 - ProviderManager implements AuthenticationManager and override authenticate() and support() method for different providers like inmemory , ldap and jdbc
 - AuthenticationProvider talks wtih Identity Store andusing username and get the `UserDetails` object
```java
	public interface UserDetails extends Serializable {
	    Collection<? extends GrantedAuthority> getAuthorities();

	    String getPassword();

	    String getUsername();

	    boolean isAccountNonExpired();

	    boolean isAccountNonLocked();

	    boolean isCredentialsNonExpired();

	    boolean isEnabled();
	}

```
---




