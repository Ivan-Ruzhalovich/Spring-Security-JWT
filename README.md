<h1 align="center">Hi there, I'm <a>Ruzhalovich Ivan</a> 
<img src="https://github.com/blackcater/blackcater/raw/main/images/Hi.gif" height="32"/></h1>

<h2 align="center">This is Spring-Security-JWT</h2>

План задания: Реализация JWT аутентификации с кастомными ролями
Цель:
Реализовать JWT аутентификацию в Spring Boot приложении с использованием кастомных ролей и прав доступа к определённым эндпоинтам. Также реализовать блокировку аккаунтов после нескольких неудачных попыток входа и логирование событий аутентификации. Обеспечить безопасность передачи данных с помощью HTTPS.

<h2>Шаги задания:</h2>
<h3>1. Настройка Spring Security</h3>
Настройте проект Spring Boot с зависимостью на Spring Security и JWT.
Настройте SecurityConfig, чтобы приложение поддерживало JWT-аутентификацию.
Разрешите доступ к определённым ресурсам (например, /login) без аутентификации.
@Configuration  
@EnableWebSecurity  
@AllArgsConstructor  
public class SecurityConfig {  
  
    private final OurUserDetailedService ourUserDetailedService;  
  
    private JwtAuthenticationFilter jwtAuthenticationFilter;  
  
    private final LoggingFilter loggingFilter;  
  
     @Bean  
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {  
		// Ваша логика
		// Использование базовой аутентификации (опционально)  
	    // Перенаправление HTTP на HTTPS  
		
        return httpSecurity.build();  
    }  
  
  
    @Bean  
    public AuthenticationProvider authenticationProvider() {  
	// Установка сервиса для загрузки пользовательских данных  
     // Установка PasswordEncoder для проверки паролей  
    }  
  
    @Bean  
    public PasswordEncoder passwordEncoder() {  
        return new BCryptPasswordEncoder();  
    }  

	@Bean  
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {  
        return authenticationConfiguration.getAuthenticationManager();  
    }  
}

<h3>2. Модель пользователя с ролями</h3>
Создайте модель User с полями username, password, и role. Роль может быть ENUM значением, например, "USER", "MODERATOR", "SUPER_ADMIN".
Добавьте поле isAccountNonLocked, чтобы управлять блокировкой 

<h3>3. Генерация JWT-токенов</h3>
Реализуйте сервис для генерации и валидации JWT-токенов.
При успешной аутентификации генерируйте JWT, добавляйте информацию о ролях и срок действия токена.
Возвращайте JWT в ответе при успешной аутентификации.
@Component  
public class JWTUtils {  
  
    // Ключ шифрования для JWT  
    private SecretKey secretKey;  
  
    // Время действия токена в миллисекундах (24 часа)  
    private static final long EXPIRATION_TIME;
  
    public JWTUtils(){  
        // Строка, используемая для создания секретного ключа  
        String secreteString = //
        byte[] keyBytes = //
    }  
    /*Метод для генерации JWT токена на основе данных пользователя*/  
    public String generateToken(UserDetails userDetails){  
        
    }  
  
    // Метод для генерации токена обновления (refresh token) с дополнительными данными  
    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){  
    }  
  
    public String extractUsername(String token) {  
        return extractClaims(token, Claims::getSubject);  
    }  
  
    // Метод для извлечения имени пользователя из токена  
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction {  
    }  
  
    public boolean isTokenValid(String token, UserDetails userDetails) {  

  
    private boolean isTokenExpired(String token) {  
        return extractClaims(token, Claims::getExpiration).before(new Date());  
    }  
}

<h3>4. Аутентификация с использованием JWT</h3>
Настройте фильтр JwtAuthenticationFilter, который будет перехватывать запросы и проверять JWT-токен.
Если токен валиден и не истек, добавляйте аутентификацию пользователя в SecurityContext.
@Component  
@AllArgsConstructor  
public class JwtAuthenticationFilter extends OncePerRequestFilter {  
  
    private final JWTUtils jwtUtils;  
  
    private OurUserDetailedService ourUserDetailedService;  
  
    // Метод, выполняемый для каждого HTTP запроса  
    @Override  
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)  
            throws ServletException, IOException {  
  
        // Шаг 1: Извлечение заголовка авторизации из запроса  

        // Шаг 2: Проверка наличия заголовка авторизации  
        
        // Шаг 3: Извлечение токена из заголовка  
  
        // Шаг 4: Извлечение имени пользователя из JWT токена
                
        // Шаг 5: Проверка валидности токена и аутентификации  
     
        // Шаг 6: Создание нового контекста безопасности  
      
        // Шаг 7: Передача запроса на дальнейшую обработку в фильтрующий цепочке  
    }  
  
}

<h3>5. Настройка кастомных ролей и доступа к эндпоинтам</h3>
Создайте несколько кастомных ролей: "USER", "MODERATOR", "SUPER_ADMIN".
В контроллерах ограничьте доступ к определённым методам для пользователей с определёнными ролями:
USER — доступ к общим ресурсам (например, просмотр профиля).
MODERATOR — доступ к модерации контента.
SUPER_ADMIN — полный доступ к управлению пользователями.
Настройте роли через аннотации @PreAuthorize или @Secured на уровне методов.

<h3>6. Проверка срока действия JWT и обработка истечения</h3>
Реализуйте логику проверки срока действия токена.
Если срок действия токена истёк, обработайте это событие, отправляя соответствующий HTTP-ответ клиенту (например, 401 Unauthorized).

<h3>7. Блокировка аккаунтов после неудачных попыток входа</h3>
Создайте механизм подсчёта неудачных попыток входа.
Если количество неудачных попыток входа превышает определённый порог (например, 5 попыток), блокируйте аккаунт, установив поле isAccountNonLocked в false.
Реализуйте возможность разблокировки аккаунта администратором.

<h3>8. Логирование аутентификации</h3>
Реализуйте логирование попыток входа (успешных и неудачных) и других важных действий (например, блокировка аккаунта, генерация JWT).
Логи можно записывать в файл или базу данных для анализа.
@Component  
public class LoggingFilter extends OncePerRequestFilter {  
  
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);  
  
    @Override  
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  
            throws ServletException, IOException {  
        // Логирование информации о запросе  
    }  
}

<h3>9. Использование HTTPS</h3>
Настройте приложение для работы через HTTPS. Для этого можно использовать самоподписанный сертификат или внешний сертификат.
Убедитесь, что все запросы (особенно связанные с аутентификацией и передачей JWT) передаются через защищённое соединение. (Для создания самоподписанного сертификата в Java используется утилита keytool, которая поставляется вместе с JDK)

<h3>10. **Тестирование</h3>
Тестируйте приложение с разными пользователями и ролями, проверяя доступ к эндпоинтам.
Проверьте истечение токена и процесс блокировки аккаунта после нескольких неудачных попыток входа.
Убедитесь, что всё логирование работает корректно.
Дополнительные требования (опционально):
Реализуйте возможность обновления JWT через Refresh Token.
