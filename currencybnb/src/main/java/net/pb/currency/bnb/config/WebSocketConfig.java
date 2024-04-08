package net.pb.currency.bnb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

//@Configuration
//@EnableWebFlux
public class WebSocketConfig implements WebFluxConfigurer {

//    @Bean
//    public WebSocketHandlerAdapter handlerAdapter() {
//        return new WebSocketHandlerAdapter();
//    }

//    @Bean
//    public SimpleUrlHandlerMapping urlHandlerMapping(CurrencyService currencyService) {
//        Map<String, WebSocketHandler> urlMap = new HashMap<>();
//        urlMap.put("/currency/search", currencyService);
//        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
//        handlerMapping.setOrder(1);
//        handlerMapping.setUrlMap(urlMap);
//        return handlerMapping;
//    }
}
