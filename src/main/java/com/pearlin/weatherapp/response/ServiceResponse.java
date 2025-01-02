/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pearlin.weatherapp.response;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pearlin
 */
public class ServiceResponse <T> {
    private List<ErrorDetail> errors;
    private T response;

    public ServiceResponse() {
        this.errors = new ArrayList<>();
    }

    public void addError(Exception error) {
        this.errors.add(new ErrorDetail(error.getMessage(), error.getClass().getSimpleName()));
    }

    public void addErrors(List<Exception> exceptions) {
        for (Exception exception : exceptions) {
            addError(exception);
        }
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public T getResponse() {
        return response;
    }
}
