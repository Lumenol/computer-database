package com.excilys.cdb.persistence.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class TransactionConnectionProxy implements InvocationHandler {

    private final Connection connection;

    private TransactionConnectionProxy(Connection connection) {
	this.connection = connection;
    }

    public static Connection proxy(Connection connection) {
	return (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[] { Connection.class },
		new TransactionConnectionProxy(connection));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	switch (method.getName()) {
	case "close":
	case "commit":
	case "rollback":
	case "setAutoCommit":
	    return null;
	default:
	    return method.invoke(connection, args);
	}
    }
}
