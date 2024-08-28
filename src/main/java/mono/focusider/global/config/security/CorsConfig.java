package mono.focusider.global.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("http://localhost:3000", "http://localhost:3001", "http://3.38.179.245:3000",
                        "http://focusider.shop:3000", "http://focusider.shop", "https://focusider.shop",
                        "http://localhost.focusider:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
    }
}
