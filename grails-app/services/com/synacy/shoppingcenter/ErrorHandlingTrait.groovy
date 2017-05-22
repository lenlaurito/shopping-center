package com.synacy.shoppingcenter

import org.springframework.http.HttpStatus
import grails.validation.ValidationException

trait ErrorHandlingTrait  {

    def handleValidatioException(ValidationException e) {
        respond(e)
    }

    def handleResourceNotFoundException(ResourceNotFoundException e) {
        response.status = HttpStatus.NOT_FOUND.value()
        respond([error: e.getMessage()])
    }

    def handleInvalidRequestException(InvalidRequestException e) {
        response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        respond([error: e.getMessage()])
    }
}