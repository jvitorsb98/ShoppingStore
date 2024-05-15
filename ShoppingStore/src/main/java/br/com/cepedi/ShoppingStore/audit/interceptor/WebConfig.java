package br.com.cepedi.ShoppingStore.audit.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

 

    @Autowired
    private IpAddressInterceptor ipAddressInterceptor;    

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
  
        registry.addInterceptor(ipAddressInterceptor); //para a opção via AOP
    }
}