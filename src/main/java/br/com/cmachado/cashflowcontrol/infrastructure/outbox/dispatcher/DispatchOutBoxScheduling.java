package br.com.cmachado.cashflowcontrol.infrastructure.outbox.dispatcher;

import br.com.cmachado.cashflowcontrol.infrastructure.outbox.OutBoxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class DispatchOutBoxScheduling {
    static final Logger logger = LoggerFactory.getLogger(DispatchOutBoxScheduling.class);

    private final OutBoxRepository outBoxRepository;
    private final QueueDispatcherFactory queueDispatcherFactory;

    public DispatchOutBoxScheduling(OutBoxRepository outBoxRepository,
                                    QueueDispatcherFactory queueDispatcherFactory) {
        this.outBoxRepository = outBoxRepository;
        this.queueDispatcherFactory = queueDispatcherFactory;
    }

    @Scheduled(fixedDelay = 15000)
    public void execute() {
        var outBoxes = outBoxRepository.findByDispatchedFalseOrderByCreatedAt();

        if (!outBoxes.isPresent())
            return;

        for (var outBox : outBoxes.get()) {
            logger.info("Sending " + outBox.toString() + " to queue.");

            try {
                var dispatcher = queueDispatcherFactory.dispatcherFor(outBox.getAggregate());

                dispatcher.dispatch(outBox);

                outBox.dispatch();

                outBoxRepository.save(outBox);
            } catch (Exception e) {
                logger.info("Error on send " + outBox + " to queue. Continue next outbox");
                continue;
            }

            logger.info("Success on send " + outBox + " to queue.");
        }
    }
}
