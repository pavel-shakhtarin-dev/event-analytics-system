package ru.shpg.eventreceiver.security.util;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.security.enabled", havingValue = "false")
public class LocalUserProvider implements UserProvider {

    @Override
    public String getUserId() {
        return "local-dev-user";
    }

}

