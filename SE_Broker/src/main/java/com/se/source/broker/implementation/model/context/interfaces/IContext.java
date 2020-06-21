package com.se.source.broker.implementation.model.context.interfaces;

import javax.servlet.http.HttpServletRequest;

public interface IContext {
    public void initialiseContext(HttpServletRequest request);

    public void setCurrentSessionInfo(HttpServletRequest request);

    public void setCurrentServiceInfo();

    public void setBody(String body);
}
