package be.vdab.orders.orders;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WerknemerService {
    private final WerknemerRepository werknemerRepository;

    public WerknemerService(WerknemerRepository werknemerRepository) {
        this.werknemerRepository = werknemerRepository;
    }

    public Werknemer findById(int id){
        return werknemerRepository.findById(id).orElseThrow(()->new WerknemerNietGevondenException(id));
    }
}
