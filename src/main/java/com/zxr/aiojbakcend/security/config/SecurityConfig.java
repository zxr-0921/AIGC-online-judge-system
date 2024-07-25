package com.zxr.aiojbakcend.security.config;

import com.zxr.aiojbakcend.security.filter.JwtAuthenticationTokenFilter;
import com.zxr.aiojbakcend.security.service.handler.AccessDeniedHandlerImpl;
import com.zxr.aiojbakcend.security.service.handler.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置类
 */
@Configuration //配置类
@EnableWebSecurity // 开启Spring Security的功能 代替了 implements WebSecurityConfigurerAdapter
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    /**
     * 配置密码编码器
     *
     * @return 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置认证管理器
     *
     * @return 认证管理器
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 配置安全过滤链
     *
     * @param http HttpSecurity对象
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 禁用CSRF保护,关闭csrf验证(防止跨站请求伪造攻击)由于我们的资源都会收到SpringSecurity的保护，所以想要跨域访问还要让SpringSecurity运行跨域访问
                .csrf(csrf -> csrf.disable())
                // 设置会话创建策略为无状态,不通过session 获取SecurityContext(基于Token不需要session)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 配置授权规则
                .authorizeRequests(authorizeRequests ->
                                authorizeRequests
                                        // 允许登录接口匿名访问
                                        .requestMatchers("/user/login", "/user/register").anonymous()
                                        // 允许所有用户访问
//                                .requestMatchers("/user/register").permitAll()
                                        // swagger-ui放行
                                        .requestMatchers("/doc.html", "/webjars/**",
                                                "/swagger-resources", "/swagger-resources/**",
                                                "/v3/**", "/favicon.ico", "Mozilla/**", "/swagger-ui.html").permitAll()
                                        // 其他接口需要认证
                                        .anyRequest().authenticated()
                )
                // 开启跨域配置
                .cors(AbstractHttpConfigurer::disable)
//         添加自定义过滤器,在UsernamePasswordAuthenticationFilter之前添加
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置异常处理
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint));

        // 构建并返回安全过滤链
        return http.build();
    }

    // 测试密码加密
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456789");
        System.out.println(encode);
    }
}