package com.example.modeling_tools.service;

import com.example.modeling_tools.endpoint.dto.ModelingToolDto;
import com.example.modeling_tools.endpoint.dto.ModelingToolSearchDto;
import com.example.modeling_tools.endpoint.dto.ModelingToolSuggestionDto;
import com.example.modeling_tools.exception.DuplicateException;
import com.example.modeling_tools.exception.FatalException;
import com.example.modeling_tools.exception.NotFoundException;
import com.example.modeling_tools.exception.ValidationException;

import java.util.List;

/**
 * Service responsible for the storage and retrieval of Modeling Tools and Modeling Tool Suggestions.
 */
public interface ModelingToolService {

    /**
     * Get all modeling tools stored in the persistence database.
     *
     * @param searchDto modeling tool search parameters
     * @return Stream of modeling tool dto's
     */
    List<ModelingToolDto> getModelingTools(ModelingToolSearchDto searchDto);

    /**
     * Get modeling tool with the corresponding ID stored in the persistence database.
     *
     * @param id the ID of the modeling tool
     * @return the modeling tool with ID {@code id}
     * @throws NotFoundException Thrown if a modeling tool with the corresponding ID does not exist
     */
    ModelingToolDto getModelingToolById(Long id) throws NotFoundException;

    /**
     * Retrieves all modeling tool suggestions stored in the persistent data store.
     *
     * @return a list of modeling tool suggestions
     */
    List<ModelingToolSuggestionDto> getModelingToolSuggestions();


    /**
     * Store a modeling tool suggestion in the persistent data store.
     *
     * @param suggestionDto data of the new modeling tool suggestion
     * @param ignoreMail specifies whether a mail should be sent out to the user and the admin (useful for test purposes)
     * @return modeling tool DTO
     * @throws ValidationException if provided suggestion is syntactically invalid
     */
    ModelingToolDto storeModelingToolSuggestion(ModelingToolSuggestionDto suggestionDto, Boolean ignoreMail) throws ValidationException;

    /**
     * Stores a suggested modeling tool in the persistent data store.
     *
     * @param suggestionDto details of the suggested modeling tool
     * @param id corresponding id of the already existing modeling tool
     * @param ignoreMail specifies whether a mail should be sent out to the user and the admin (useful for test purposes)
     * @return a modeling tool with the newly updated information
     * @throws DuplicateException if the modeling tool edit suggestion contains the same values as the modeling tool it
     *  tries to edit + does not contain a feedback
     * @throws NotFoundException if the modeling tool with the corresponding {@code id} does not exist
     * @throws ValidationException if the modeling tool does not match certain criteria
     */
    ModelingToolDto updateModelingToolSuggestion(ModelingToolSuggestionDto suggestionDto, Long id, Boolean ignoreMail) throws DuplicateException, NotFoundException, ValidationException;

    /**
     * Confirms that data for a modeling tool is valid and stores in the persistent data store.
     *
     * @param token token matching the token of a modeling tool suggestion
     * @return HTML confirmation
     * @throws FatalException if there are more than just one modeling tool suggestions stored in the persistent data store
     * @throws NotFoundException if a modeling tool suggestion with the corresponding token could not be found
     */
    String postConfirmModelingToolSuggestion(String token) throws FatalException, NotFoundException;

    /**
     * Removes a modeling tool suggestion from the persistent data store.
     *
     * @return HTML confirmation
     * @param token token matching a modeling tool suggestion token
     * @throws NotFoundException if a modeling tool suggestion with the corresponding token could not be found
     */
    String removeModelingToolSuggestion(String token) throws NotFoundException;
}
