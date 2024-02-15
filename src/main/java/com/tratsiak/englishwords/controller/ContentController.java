package com.tratsiak.englishwords.controller;

import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.repository.WordRepository;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RestController
@RequestMapping("/content")
public class ContentController {

    private final WordRepository wordRepository;

    @Autowired
    public ContentController(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @GetMapping
    private void getContent() throws SSLException {
        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();

        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));

        WebClient client = WebClient.builder()
                .baseUrl("https://wooordhunt.ru/data/sound/sow/uk/")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        List<Word> wordList = wordRepository.findAll();

        wordList.forEach(word -> {
                    try {
                        byte[] bytes = client.get()
                                .uri(uriBuilder -> uriBuilder.path(word.getEnglish() + ".mp3").build())
                                .accept(MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                                .retrieve()
                                .onStatus(HttpStatusCode::isError, error -> Mono.error(new RuntimeException(String.valueOf(error.statusCode()))))
                                .bodyToMono(byte[].class).block();
                        assert bytes != null;
                        Path path = Paths.get("D:\\LearningWords\\" + word.getEnglish() + ".mp3");
                        Files.write(path, bytes);
                        word.setSound(true);

                    } catch (RuntimeException ignored) {
                        word.setSound(false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } finally {
                        wordRepository.save(word);
                    }

                }
        );
    }
}
