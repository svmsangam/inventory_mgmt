package com.inventory.core.lock;

import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by dhiraj on 10/13/17.
 */
@Service
public class PaymentLock {

    private static final int MAX_AVAILABLE = 1;

    private final static Semaphore semaphore = new Semaphore(MAX_AVAILABLE, true);


    public static boolean lockOnSave(long invoiceId){
        boolean permit = false;
        long usedId = 0;
        long unusedId = 0;
        try {
            permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);

            if (permit & usedId != invoiceId){
                System.out.println("Semaphore acquired");
                sleep(5);
            }
            else if (permit & usedId == invoiceId & unusedId == invoiceId) {
                System.out.println("Semaphore acquired");
                sleep(5);

            } else {
                System.out.println("Could not acquire semaphore");
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        } finally {
            if (permit) {
                semaphore.release();
                unusedId = invoiceId;
                System.out.println("Semaphore release");
                return permit;
            }

            return false;
        }
    }

}
