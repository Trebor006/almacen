package com.implementation.patterns.almacen.components;

import com.google.gson.Gson;
import com.implementation.patterns.almacen.model.ComprobanteDtoRequest;
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
public class ContabilidadClient {
    private final Gson gson;

    @Autowired
    private RestTemplate restTemplate;
    @Value("${contabilidad.url}")
    private String contabilidadURL;

    public void callCreateComprobante(ComprobanteDtoRequest comprobanteDto) {
        var createComprobanteURL = contabilidadURL + "/comprobantes";

        var builder = UriComponentsBuilder.fromHttpUrl(createComprobanteURL);
        var jsonObject = this.gson.toJson(comprobanteDto);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var requestEntity = new HttpEntity<>(jsonObject, headers);

        this.restTemplate.postForEntity(
                UriUtils.decode(builder.toUriString(), StandardCharsets.UTF_8),
                requestEntity,
                String.class
        );
    }
}
