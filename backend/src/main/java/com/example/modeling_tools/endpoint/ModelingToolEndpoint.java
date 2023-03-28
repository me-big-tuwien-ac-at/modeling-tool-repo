package com.example.modeling_tools.endpoint;

import com.example.modeling_tools.endpoint.dto.*;
import com.example.modeling_tools.exception.DuplicateException;
import com.example.modeling_tools.exception.NotFoundException;
import com.example.modeling_tools.exception.ValidationException;
import com.example.modeling_tools.service.ModelingToolService;
import com.example.modeling_tools.service.PropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = ModelingToolEndpoint.BASE_PATH)
public class ModelingToolEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    static final String BASE_PATH = "/api/v1/modeling_tools";
    private final ModelingToolService service;
    private final PropertiesService propertiesService;

    public ModelingToolEndpoint(ModelingToolService service, PropertiesService propertiesService) {
        this.service = service;
        this.propertiesService = propertiesService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Stream<ModelingToolDto> getModelingTools(ModelingToolSearchDto searchDto) {
        LOGGER.info("GET " + BASE_PATH + searchDto.paramsAsString());
        LOGGER.debug("getModelingTools({})", searchDto);
        return service.getModelingTools(searchDto).stream();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public ModelingToolAndPropertiesDto getModelingToolsAndVerifiedProperties() {
        LOGGER.info("GET " + BASE_PATH + "/all");
        LOGGER.debug("getModelingToolsAndVerifiedProperties()");
        ModelingToolAndPropertiesDto webDto =  new ModelingToolAndPropertiesDto();
        webDto.setModelingTools(service.getModelingTools(null));
        webDto.setModelingLanguages(propertiesService.getModelingLanguages(null).toList());
        webDto.setPlatforms(propertiesService.getPlatforms(null).toList());
        webDto.setProgrammingLanguages(propertiesService.getProgrammingLanguages(null).toList());
        return webDto;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ModelingToolDto getModelingToolById(@PathVariable Long id) {
        LOGGER.info("GET " + BASE_PATH + "/{}", id);
        LOGGER.debug("getModelingToolById({})", id);
        try {
            return service.getModelingToolById(id);
        } catch (NotFoundException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            logClientError(status, "Invalid GET request for a modeling tool by id", e);
            throw new ResponseStatusException(status, e.getMessage(), e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/edit/{id}")
    public ModelingToolAndPropertiesAndEditDto getModelingToolsAndModelingToolById(@PathVariable Long id) {
        LOGGER.info("GET " + BASE_PATH + "edit/{}", id);
        LOGGER.debug("getModelingToolsAndModelingToolById({})", id);
        ModelingToolAndPropertiesAndEditDto webDto =  new ModelingToolAndPropertiesAndEditDto();
        webDto.setModelingTools(service.getModelingTools(null));
        try {
            webDto.setModelingTool(service.getModelingToolById(id));
        } catch (NotFoundException e) {
            webDto.setModelingTool(null);
        }
        webDto.setModelingLanguages(propertiesService.getModelingLanguages(null).toList());
        webDto.setPlatforms(propertiesService.getPlatforms(null).toList());
        webDto.setProgrammingLanguages(propertiesService.getProgrammingLanguages(null).toList());
        return webDto;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/suggestions")
    public Stream<ModelingToolSuggestionDto> getModelingToolSuggestions() {
        LOGGER.info("GET " + BASE_PATH + "/suggestions");
        LOGGER.debug("getModelingToolSuggestions()");
        return service.getModelingToolSuggestions().stream();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ModelingToolDto storeModelingTool(
            @RequestBody ModelingToolSuggestionDto suggestionDto,
            @RequestParam(required = false) Boolean ignoreMail
    ) {
        LOGGER.info("POST " + BASE_PATH + (ignoreMail == null ? "" : (ignoreMail ? "?ignoreMail=true" : "?ignoreMail=false")));
        LOGGER.debug("storeModelingTool({})", suggestionDto);
        try {
            return service.storeModelingToolSuggestion(suggestionDto, ignoreMail);
        } catch (NotFoundException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            logClientError(status, "Invalid POST request to the modeling tool endpoint", e);
            throw new ResponseStatusException(status, e.getMessage(), e);
        } catch (ValidationException e) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
            logClientError(status, "Invalid POST request to the modeling tool endpoint", e);
            throw new ResponseStatusException(status, e.getMessage(), e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public ModelingToolDto storeModelingToolEditSuggestion(
            @RequestBody ModelingToolSuggestionDto suggestionDto,
            @PathVariable Long id,
            @RequestParam(required = false) Boolean ignoreMail
    ) {
        LOGGER.info("PUT " + BASE_PATH + "/" + id + (ignoreMail == null ? "" : (ignoreMail ? "?ignoreMail=true" : "?ignoreMail=false")));
        LOGGER.debug("storeModelingToolEditSuggestion({})", suggestionDto);
        try {
            return service.updateModelingToolSuggestion(suggestionDto, id, ignoreMail);
        } catch (NotFoundException e) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            logClientError(status, "Invalid PUT request to the modeling tool endpoint", e);
            throw new ResponseStatusException(status, e.getMessage(), e);
        } catch (DuplicateException | ValidationException e) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
            logClientError(status, "Invalid PUT request to the modeling tool endpoint", e);
            throw new ResponseStatusException(status, e.getMessage(), e);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/confirm")
    public ResponseEntity<String> postConfirmModelingToolSuggestion(@RequestParam String token) {
        LOGGER.info("GET " + BASE_PATH + "/confirm?token=" + token);
        LOGGER.debug("postConfirmModelingToolSuggestion({})", token);
        try {
            // return service.postConfirmModelingToolSuggestion(token);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.postConfirmModelingToolSuggestion(token));
        } catch (NotFoundException e) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            logClientError(
                    status,
                    "Invalid GET request to the modeling tool endpoint for confirming a suggestion",
                    e,
                    "Modeling Tool Suggestion with specified token does not exist"
            );
            return ResponseEntity.status(status).body(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/delete")
    public ResponseEntity<String> removeModelingToolSuggestion(@RequestParam String token) {
        LOGGER.info("DELETE " + BASE_PATH + "/delete?token=" + token);
        LOGGER.debug("removeModelingToolSuggestion({})", token);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.removeModelingToolSuggestion(token));
        } catch (NotFoundException e) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            logClientError(
                    status,
                    "Invalid GET request to the modeling tool endpoint for deleting a suggestion",
                    e,
                    "Modeling Tool Suggestion with specified token does not exist"
            );
            return ResponseEntity.status(status).body(e.getMessage());
        }
    }

    /**
     * Method for logging client errors.
     *
     * @param status Http status code of the response sent
     * @param message Message sent to the client
     * @param e Exception that arose because of the error
     */
    private void logClientError(HttpStatus status, String message, Exception e) {
        LOGGER.warn("{} {}: {} - {}", status.value(), message, e.getClass().getSimpleName(), e.getMessage());
    }

    private void logClientError(HttpStatus status, String message, Exception e, String exceptionMessage) {
        LOGGER.warn("{} {}: {} - {}", status.value(), message, e.getClass().getSimpleName(), exceptionMessage);
    }
}
