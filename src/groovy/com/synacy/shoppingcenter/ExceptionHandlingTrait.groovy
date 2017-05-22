package com.synacy.shoppingcenter

import grails.validation.ValidationException
import com.synacy.shoppingcenter.exceptions.*
import org.springframework.http.HttpStatus

import javax.naming.LimitExceededException

trait ExceptionHandlingTrait {


    def handleResourceNotFoundException(ResourceNotFoundException e) {
        response.status = HttpStatus.NOT_FOUND.value()
        respond([error: e.getMessage()])
    }

    def handleExistingResourceException(ExistingResourceException e) {
        response.status = HttpStatus.BAD_REQUEST.value()
        respond([error: e.getMessage()])
    }

    def handleInvalidLocationException(InvalidLocationException e) {
        response.status = HttpStatus.BAD_REQUEST.value()
        respond([error: e.getMessage()])
    }

    def handleNullPointerException(NullPointerException e) {
        response.status = HttpStatus.BAD_REQUEST.value()
        respond([error: e.getMessage()])
    }

    def handleLimitExceedException(LimitExceededException e) {
        response.status = HttpStatus.BAD_REQUEST.value()
        respond([error: e.getMessage()])
    }

}
