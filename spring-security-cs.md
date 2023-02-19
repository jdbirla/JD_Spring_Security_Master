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
## JDBC Authentication with spring security
  - Setup datasourcer it might be in-memory or my sql , oracle in property file and inject DataSource object into cofigure methods
  - ![image](https://user-images.githubusercontent.com/69948118/219939189-e09e2290-ae75-4c8a-a0db-6d8555387fe2.png)
  - We can create our own table strucuter but we have to provide like usernae, password , enable and required information for authroties and we can put custom query in cofigure method to fetch data from our custom tables
  -![image](https://user-images.githubusercontent.com/69948118/219939305-aee22a80-f75a-4589-9ae3-ce2542e33eb6.png)

## JPA Authentication with spring security
- create user table in mysqldb
- AuthenticationProvided -> UserDetailsService -> loadUserByUsername() -> JPARepository -> findByUserName() from DB
- ![image](https://user-images.githubusercontent.com/69948118/219939501-96eb5cbe-37ee-4947-b16b-20b21f167c25.png)
- ```java
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> byUserName = userRepository.findByUserName(username);
        byUserName.orElseThrow(() -> new UsernameNotFoundException("user not found : "+username));
        return byUserName.map(MyUserDetails::new).get();

    }
    ```
    ```java
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

    }
    ```

## Sping Boot Security with LDAP

    - LDAP : Lightweight Directory Access Protocol which mains data into tree hirarchay
    - We need dependcey for security for the LDAP `spring-security-ldap`
    - As we are running ladp locally we need to setup ladpa also for that we need to add dependecny `unboundid-ldapsdk`
    -  User information store in ldap file extension is `ldif`
    
 ```
		      dn: uid=ben,ou=people,dc=springframework,dc=org
		objectclass: top
		objectclass: person
		objectclass: organizationalPerson
		objectclass: inetOrgPerson
		cn: Ben Alex
		sn: Alex
		uid: ben
		userPassword: $2a$10$c6bSeWPhg06xB1lvmaWNNe4NROmZiSpYhlocU/98HNr2MhIOiSt36
		
```
```java
   
           @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userPassword");


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and().formLogin();
    }
    
 ```
## JWT Json Web Tokens



