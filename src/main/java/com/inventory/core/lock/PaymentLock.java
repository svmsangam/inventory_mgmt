package com.inventory.core.lock;

import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by dhiraj on 10/13/17.
 */
@Service
public class PaymentLock {

    private static final int MAX_AVAILABLE = 1;

    private final static Semaphore semaphore = new Semaphore(MAX_AVAILABLE, true);

    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    private static long usedId = 0;
    private static long unusedId = 0;

    public static boolean lockOnSave(long invoiceId){
        boolean permit = false;

        try {
            permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);

            if (permit & usedId != invoiceId){
                System.out.println("Semaphore acquired");
                lock.writeLock().lock();
                System.out.println("Lock acquired");
            }
            else if (permit & usedId == invoiceId & unusedId == invoiceId) {
                System.out.println("Semaphore acquired");
                lock.writeLock().lock();
                System.out.println("Lock acquired");

            } else {
                System.out.println("Could not acquire semaphore");
            }
        } catch (InterruptedException e) {
            lock.writeLock().unlock();
            semaphore.release();
            unusedId = invoiceId;
            System.out.println("Semaphore release");
            return permit;
        }

        return false;

    }

    public static void unlockOnSave(long invoiceId){
        lock.writeLock().unlock();
        semaphore.release();
        unusedId = invoiceId;
    }

}
