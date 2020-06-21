package com.se.source.broker.implementation.model.context;

import com.se.source.broker.domain.Provider;

public class ContextAttributeServiceInfo {

    private Provider provider;

    public ContextAttributeServiceInfo(Provider provider) {
        this.provider = provider;
    }

    public Provider getProvider() {
        return provider;
    }
}
