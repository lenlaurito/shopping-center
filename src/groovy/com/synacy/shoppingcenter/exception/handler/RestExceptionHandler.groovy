package com.synacy.shoppingcenter.exception.handler

import com.synacy.shoppingcenter.exception.*
import org.springframework.http.HttpStatus

trait RestExceptionHandler {

    def handleResourceNotFound(ResourceNotFoundException e) {
        response.status = HttpStatus.NOT_FOUND.value()
        respond([error: e.getMessage()])
    }

    def handleInvalidRequest(InvalidRequestException e) {
        response.status = HttpStatus.UNPROCESSABLE_ENTITY.value()
        respond([error: e.getMessage()])
    }

    def handleRequestConflicts(RequestConflictViolationException e) {
        response.status = HttpStatus.CONFLICT.value()
        respond([error: e.getMessage()])
    }

}
