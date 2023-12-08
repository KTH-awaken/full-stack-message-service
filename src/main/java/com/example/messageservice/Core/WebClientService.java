package com.example.messageservice.Core;

import com.example.messageservice.View.ViewModels.AccountVm;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {
    private final WebClient webClient;

    public WebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<AccountVm> fetchUserFromUserService(String url, String authHeader) {
        return webClient.get()
                .uri(url)
                .header("Authorization", authHeader)
                .retrieve()
                .bodyToMono(AccountVm.class);
    }
}
