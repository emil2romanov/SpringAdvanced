package bg.softuni.blacklist.service;

import org.springframework.stereotype.Service;

@Service
public class BlackListService {

    public boolean isBlackListed(String ipAddress) {

        return true;
    }
}
