package com.enigma.tokonyadia.utils;


public interface MessageResultConstant {
    public static final String CREATE_SUCCESS_PRODUCT = Message.succesCreateMessageProduct();
    public static final String UPDATE_SUCCESS_PRODUCT = Message.succesUpdateMessageProduct() ;
    public static final String DELETE_SUCCESS_PRODUCT = Message.succesDeleteMessageProduct();
    public static final String NOT_FOUND_PRODUCT = Message.notFoundMessageProduct();

    public static final String CREATE_SUCCESS_CUSTOMER = Message.succesCreateMessageCustomer();
    public static final String UPDATE_SUCCESS_CUSTOMER = Message.succesUpdateMessageCustomer();
    public static final String DELETE_SUCCESS_CUSTOMER = Message.succesDeleteMessageCustomer();
    public static final String NOT_FOUND_CUSTOMER = Message.notFoundMessageCustomer();
} 
