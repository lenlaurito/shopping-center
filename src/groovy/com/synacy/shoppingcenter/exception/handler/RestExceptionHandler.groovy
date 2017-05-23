package com.synacy.shoppingcenter.exception.handler

import com.synacy.shoppingcenter.exception.*
import org.springframework.http.HttpStatus
	
trait RestExceptionHandler {

    def handleResourceNotFound(ResourceNotFoundException e) {
        response.status = HttpStatus.NOT_FOUND.value()
        respond([status: response.status, error: e.getMessage()])
    }

    def handleInvalidRequest(InvalidRequestException e) {
        response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        respond([status: response.status, error: e.getMessage()])
    }

    def handleRequestConflicts(RequestConflictViolationException e) {
        response.status = HttpStatus.CONFLICT.value()
        respond([status: response.status, error: e.getMessage()])
    }
	
	def handleNumberFormatValidation(NumberFormatException e) {
		response.status = HttpStatus.BAD_REQUEST.value()
		respond([status: response.status, error: e.getMessage()])
	}

}
