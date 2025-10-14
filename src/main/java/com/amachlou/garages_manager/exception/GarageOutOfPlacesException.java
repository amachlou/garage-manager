package com.amachlou.garages_manager.exception;

public class GarageOutOfPlacesException extends RuntimeException {

    public GarageOutOfPlacesException(String errorMessage) {
        super(errorMessage);
    }

}

