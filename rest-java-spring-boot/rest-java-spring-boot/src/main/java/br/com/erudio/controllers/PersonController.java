package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.services.PersonServices;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for managing People ")
public class PersonController {

	@Autowired
	private PersonServices service;

	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })

	@Operation(summary = "Finds all people", description = "Finds all people", tags = { "People" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {

					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))

			}), @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })

	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction
			) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.DESC;
		Pageable  pageable = PageRequest.of(page,size,Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findAll(pageable));
	}







	@GetMapping( value = "/findPersonsByNames/{firstName}",
			produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })

	@Operation(summary = "Finds people by name ", description = "Finds people by name", tags = { "People" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {

					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PersonVO.class)))

			}), @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })

	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findPersonByNames(
			@PathVariable(value = "firstName") String firstName,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "10") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.DESC;
		Pageable  pageable = PageRequest.of(page,size,Sort.by(sortDirection, "firstName"));
		return ResponseEntity.ok(service.findPersonByName(firstName,pageable));
	}








	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML })
	@Operation(summary = "Find a person", description = "Find a person", tags = { "People" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	@CrossOrigin(origins = {"http://localhost:8080", "https://erudio.com.br"} )
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML })

	@Operation(summary = "Adds a new person ", description = "Adds a new person /JSON,XML or YML", tags = {
			"People" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public PersonVO create(@RequestBody PersonVO person) {
		return service.create(person);
	}

	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML })

	@Operation(summary = "Update a  person ", description = "Update a  person/JSON,XML or YML", tags = { "People" }, responses = {
			@ApiResponse(description = "Update", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public PersonVO update(@RequestBody PersonVO person) {
		return service.update(person);
	}



	@PatchMapping (value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML })
	@Operation(summary = "Disable a specific person by your id", description = "Disable a specific person by your id", tags = { "People" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = PersonVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public PersonVO disablePerson(@PathVariable(value = "id") Long id) {
		return service.disablePerson(id);
	}



	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Delete a  person", description = "Delete", tags = { "People" }, responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content) })
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
}
