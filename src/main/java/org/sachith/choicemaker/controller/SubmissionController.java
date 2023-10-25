package org.sachith.choicemaker.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.sachith.choicemaker.exception.MalformedSubmissionFoundException;
import org.sachith.choicemaker.exception.SocketCommunicationFailureException;
import org.sachith.choicemaker.model.Session;
import org.sachith.choicemaker.model.Submission;
import org.sachith.choicemaker.model.dto.SubmissionRequest;
import org.sachith.choicemaker.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class SubmissionController {

    @Autowired
    SubmissionService submissionService;

    @Operation(summary = "Create a submission with given details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Submission created and returned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Session.class)) }),
            @ApiResponse(responseCode = "422", description = "Session data is malformed and can not be processed",
                    content = { @Content(mediaType = "*/*",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "500", description = "Socket communication failed while broadcasting",
                    content = { @Content(mediaType = "*/*",
                            schema = @Schema(implementation = String.class)) })})
    @PostMapping("/submission")
    public ResponseEntity<Submission> createSubmission(@Parameter(description = "Submission data to create")
                                                           @RequestBody SubmissionRequest submissionRequest)
            throws MalformedSubmissionFoundException, SocketCommunicationFailureException {
        Submission submission = submissionService.createSubmission(submissionRequest);
        return ResponseEntity.ok(submission);
    }

    @Operation(summary = "Choose a submission in the session and update and broadcast to session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated and broadcast the changes to the session",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Session.class)) }),
            @ApiResponse(responseCode = "422", description = "Session data is malformed and can not be processed",
                    content = { @Content(mediaType = "*/*",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "500", description = "Socket communication failed while broadcasting",
                    content = { @Content(mediaType = "*/*",
                            schema = @Schema(implementation = String.class)) })})
    @GetMapping("/selectSubmission")
    public ResponseEntity<Submission> selectSubmission(@Parameter(description = "Submission id to update and broadcast as selected one")
                                                           @RequestParam int submissionId)
            throws MalformedSubmissionFoundException, SocketCommunicationFailureException {
        Submission submission = submissionService.selectSubmission(submissionId);
        return ResponseEntity.ok(submission);
    }
}
