package com.example.declarativetransactiondemo;

public interface FooService {

    void insert();

    void insertThenRollback() throws RollbackException;

    void invokeInsertThenRollback() throws RollbackException;
}
