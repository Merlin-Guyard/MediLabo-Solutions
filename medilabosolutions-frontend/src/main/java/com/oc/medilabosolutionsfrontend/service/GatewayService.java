package com.oc.medilabosolutionsfrontend.service;

import com.oc.medilabosolutionsfrontend.model.User;
import com.oc.medilabosolutionsfrontend.proxy.GatewayProxy;
import org.springframework.stereotype.Service;

@Service
public class GatewayService {

    private final GatewayProxy gatewayProxy;

    public GatewayService(GatewayProxy gatewayProxy) {
        this.gatewayProxy = gatewayProxy;
    }

    public boolean login(User user) {
        return gatewayProxy.login(user);
    }

    public boolean verify() {
        return gatewayProxy.verify();
    }
}
