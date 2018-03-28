package services;

import org.springframework.stereotype.Service;

/**
 * Created by Nurzhan on 31.10.2017.
 */
@Service
public interface viewFormService {

    // Узнать доступ у домена
    public Boolean getAccessDomain(String serverName);
}
