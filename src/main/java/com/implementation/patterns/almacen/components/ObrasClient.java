package com.implementation.patterns.almacen.components;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ObrasClient {

    private final Gson gson;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${obras.url}")
    private String obrasURL;

    public void callActualizarEstado(Integer idRegistroExterno) {
        var actualizarEstado = obrasURL + "/v1/solicitud/" + idRegistroExterno;
        var builder = UriComponentsBuilder.fromHttpUrl(actualizarEstado);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(headers);

        this.restTemplate.put(
                UriUtils.decode(builder.toUriString(), StandardCharsets.UTF_8),
                requestEntity,
                String.class
        );
    }
}
