package com.ssafy;

import com.ssafy.db.repository.UserRepository;
import org.kurento.client.KurentoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import javax.servlet.ServletContext;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableWebSocket
public class GroupCallApplication implements WebSocketConfigurer {

	public static void main(String[] args) {
        SpringApplication.run(GroupCallApplication.class, args);
    }

//    @Bean
//    public UserRegistry registry() {
//        return new UserRegistry();
//    }
//
//    @Bean
//    public RoomManager roomManager() {
//        return new RoomManager();
//    }
//
//    @Bean
//    public CallHandler groupCallHandler() {
//        return new CallHandler();
//    }

//    @Bean
//    public KurentoClient kurentoClient() {
//        return KurentoClient.create();
//    }

//    @Bean
//    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
//        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
//        container.setMaxBinaryMessageBufferSize(32768);
//        return container;
//    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        return new StringHttpMessageConverter(StandardCharsets.UTF_8);
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(groupCallHandler(), "/groupcall");
    }
}
