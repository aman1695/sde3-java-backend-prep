# Spring Framework

## 1. Spring Core & Boot
- Dependency Injection, AOP

## 2. Spring MVC

## 3. Spring Data JPA & Hibernate

## 4. RESTful API Development

---

Add notes, code samples, and resources for each topic above. 

# 3. Spring Framework

## 3.1 Spring Core Concepts

### 3.1.1 Dependency Injection (DI)

**Constructor Injection:**
```java
@Service
public class UserService {
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

**Setter Injection:**
```java
@Service
public class UserService {
    private UserRepository userRepository;
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

**Field Injection:**
```java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
```

### 3.1.2 Inversion of Control (IoC)

```java
// Without IoC
class UserService {
    private UserRepository userRepository = new UserRepositoryImpl();
}

// With IoC
@Component
class UserService {
    @Autowired
    private UserRepository userRepository; // Spring injects the implementation
}
```

### 3.1.3 Bean Lifecycle

```java
@Component
public class MyBean implements InitializingBean, DisposableBean {
    
    @PostConstruct
    public void init() {
        // Custom initialization
    }
    
    @Override
    public void afterPropertiesSet() {
        // Spring initialization
    }
    
    @PreDestroy
    public void cleanup() {
        // Custom cleanup
    }
    
    @Override
    public void destroy() {
        // Spring cleanup
    }
}
```

## 3.2 Spring Boot

### 3.2.1 Auto-configuration

```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}

// Custom auto-configuration
@Configuration
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(DatabaseProperties.class)
public class DatabaseAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DatabaseProperties properties) {
        // Auto-configure DataSource
        return new HikariDataSource();
    }
}
```

### 3.2.2 Configuration Properties

```java
@ConfigurationProperties(prefix = "app")
@Component
public class AppProperties {
    private String name;
    private int port;
    private List<String> features;
    
    // Getters and setters
}

// application.yml
app:
  name: MyApp
  port: 8080
  features:
    - feature1
    - feature2
```

### 3.2.3 Profiles

```java
@Profile("dev")
@Component
public class DevDataSource {
    // Development database configuration
}

@Profile("prod")
@Component
public class ProdDataSource {
    // Production database configuration
}

// application-dev.yml
spring:
  datasource:
    url: jdbc:h2:mem:testdb

// application-prod.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/proddb
```

## 3.3 Spring MVC

### 3.3.1 Controller

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);
        return ResponseEntity.created(URI.create("/api/users/" + savedUser.getId()))
                .body(savedUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, 
                                         @Valid @RequestBody User user) {
        return userService.update(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
```

### 3.3.2 Request Mapping

```java
@RestController
public class ApiController {
    
    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Data> getData(@RequestParam String id) {
        // Handle GET request
    }
    
    @PostMapping(value = "/data", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createData(@RequestBody Data data) {
        // Handle POST request
    }
    
    @RequestMapping(value = "/custom", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> customMethod() {
        // Handle multiple HTTP methods
    }
}
```

### 3.3.3 Exception Handling

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("User not found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        ErrorResponse error = new ErrorResponse("Validation failed", message);
        return ResponseEntity.badRequest().body(error);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse error = new ErrorResponse("Internal server error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
```

## 3.4 Spring Data

### 3.4.1 JPA Repository

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    // Getters and setters
}

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    List<User> findByStatus(UserStatus status);
    
    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> findByNameContaining(@Param("name") String name);
    
    @Query(value = "SELECT * FROM users WHERE created_at > :date", nativeQuery = true)
    List<User> findUsersCreatedAfter(@Param("date") LocalDateTime date);
    
    boolean existsByEmail(String email);
    
    long countByStatus(UserStatus status);
}
```

### 3.4.2 Transaction Management

```java
@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Transactional(rollbackFor = Exception.class)
    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        emailService.sendWelcomeEmail(savedUser.getEmail());
        return savedUser;
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateUserStatus(Long userId, UserStatus status) {
        // This method runs in a new transaction
        userRepository.updateStatus(userId, status);
    }
}
```

### 3.4.3 MongoDB Integration

```java
@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String email;
    
    private String name;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    // Getters and setters
}

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findByEmail(String email);
    
    List<User> findByNameContainingIgnoreCase(String name);
    
    @Query("{'createdAt': {$gte: ?0}}")
    List<User> findUsersCreatedAfter(LocalDateTime date);
}
```

## 3.5 Spring Security

### 3.5.1 Basic Security Configuration

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### 3.5.2 JWT Authentication

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        try {
            final String jwt = authHeader.substring(7);
            final String username = jwtService.extractUsername(jwt);
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                if (jwtService.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Log error
        }
        
        filterChain.doFilter(request, response);
    }
}
```

### 3.5.3 Custom User Details

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}
```

## 3.6 Spring Testing

### 3.6.1 Unit Testing

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private EmailService emailService;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void shouldCreateUser() {
        // Given
        User user = new User("test@example.com", "Test User");
        User savedUser = new User(1L, "test@example.com", "Test User");
        
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        doNothing().when(emailService).sendWelcomeEmail(anyString());
        
        // When
        User result = userService.createUser(user);
        
        // Then
        assertThat(result.getId()).isEqualTo(1L);
        verify(userRepository).save(user);
        verify(emailService).sendWelcomeEmail("test@example.com");
    }
    
    @Test
    void shouldThrowExceptionWhenUserExists() {
        // Given
        User user = new User("test@example.com", "Test User");
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        
        // When & Then
        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(UserAlreadyExistsException.class);
    }
}
```

### 3.6.2 Integration Testing

```java
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class UserControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void shouldCreateUser() {
        // Given
        User user = new User("test@example.com", "Test User");
        
        // When
        ResponseEntity<User> response = restTemplate.postForEntity("/api/users", user, User.class);
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(userRepository.existsByEmail("test@example.com")).isTrue();
    }
    
    @Test
    void shouldReturnUserById() {
        // Given
        User user = userRepository.save(new User("test@example.com", "Test User"));
        
        // When
        ResponseEntity<User> response = restTemplate.getForEntity("/api/users/" + user.getId(), User.class);
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo("test@example.com");
    }
}
```

## 3.7 Interview Questions

### 3.7.1 Spring Core

**Q: What is the difference between @Component, @Service, @Repository, and @Controller?**
A:
- @Component: Generic stereotype annotation
- @Service: Business logic layer
- @Repository: Data access layer
- @Controller: Web layer (MVC)
- All are functionally equivalent, but provide semantic meaning

**Q: What is the difference between @Autowired and @Resource?**
A:
- @Autowired: Spring-specific, by type, can be used on constructor/setter/field
- @Resource: JSR-250, by name then type, can only be used on fields/setter methods
- @Autowired has more features like @Qualifier, @Primary

### 3.7.2 Spring Boot

**Q: How does Spring Boot auto-configuration work?**
A:
- Uses @Conditional annotations to check classpath and properties
- @EnableAutoConfiguration imports AutoConfigurationImportSelector
- spring.factories file contains auto-configuration classes
- Can be customized with @ConditionalOnClass, @ConditionalOnProperty

**Q: What is the difference between @SpringBootApplication and @EnableAutoConfiguration?**
A:
- @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
- @EnableAutoConfiguration only enables auto-configuration
- @SpringBootApplication is more comprehensive

### 3.7.3 Spring Security

**Q: What is the difference between authentication and authorization?**
A:
- Authentication: Verifying who you are (login)
- Authorization: Verifying what you can do (permissions)
- Authentication comes before authorization
- Spring Security handles both through SecurityContext

**Q: How does JWT work with Spring Security?**
A:
- JWT contains user information and is stateless
- Custom filter extracts JWT from Authorization header
- Validates token and sets SecurityContext
- No session storage needed 