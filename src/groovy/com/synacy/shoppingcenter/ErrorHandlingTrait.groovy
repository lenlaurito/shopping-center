package com.synacy.shoppingcenter

import org.springframework.http.HttpStatus
import grails.validation.ValidationException
import org.springframework.dao.DataIntegrityViolationException

trait ErrorHandlingTrait  {

    def handleNumberFormatException(java.lang.NumberFormatException e) {
        response.status = HttpStatus.BAD_REQUEST.value()
        respond([error: e.getMessage() + " must be integer"])
    }
    def handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        response.status = HttpStatus.BAD_REQUEST.value()
        respond([error: "Referential integrity error."])
    }

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