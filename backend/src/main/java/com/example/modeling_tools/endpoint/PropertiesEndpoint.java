package com.example.modeling_tools.endpoint;

import com.example.modeling_tools.service.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = PropertiesEndpoint.BASE_PATH)
public class PropertiesEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    static final String BASE_PATH = "/api/v1/properties";
    private final PropertiesService service;

    public PropertiesEndpoint(PropertiesService service) {
        this.service = service;
    }

    @GetMapping("modeling_languages")
    public Stream<String> getModelingLanguages(@RequestParam(required = false) Boolean confirmed) {
        LOGGER.info("GET " + BASE_PATH + "/modeling_languages");
        LOGGER.debug("getModelingLanguages()");
        return service.getModelingLanguages(confirmed);
    }

    @GetMapping("platforms")
    public Stream<String> getPlatforms(@RequestParam(required = false) Boolean confirmed) {
        LOGGER.info("GET " + BASE_PATH + "/platforms");
        LOGGER.debug("getPlatforms()");
        return service.getPlatforms(confirmed);
    }

    @GetMapping("programming_languages")
    public Stream<String> getProgrammingLanguages(@RequestParam(required = false) Boolean confirmed) {
        LOGGER.info("GET " + BASE_PATH + "/programming_languages");
        LOGGER.debug("getProgrammingLanguages()");
        return service.getProgrammingLanguages(confirmed);
    }
}
