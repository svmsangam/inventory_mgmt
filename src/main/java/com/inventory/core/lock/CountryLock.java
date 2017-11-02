package com.inventory.core.lock;

import com.inventory.core.model.dto.CountryInfoDTO;
import com.inventory.core.model.dto.LockResponseDTO;
import com.inventory.core.model.enumconstant.LockResponse;
import com.inventory.core.validation.CountryValidation;
import com.inventory.web.error.CountryError;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by dhiraj on 10/17/17.
 */
public class CountryLock {

    private static final int MAX_AVAILABLE = 100;

    private final static Semaphore semaphore = new Semaphore(MAX_AVAILABLE, true);

    private static Map<String , Boolean> isActive = new HashMap<>(MAX_AVAILABLE);// row id that is locked

    public static LockResponseDTO acquire(CountryValidation validation , CountryInfoDTO dto){

        boolean permit = false;

        LockResponseDTO result = new LockResponseDTO();

        try {
            permit = semaphore.tryAcquire(1, TimeUnit.MILLISECONDS);

            if (permit){

                System.out.println("Semaphore acquired");
                sleep(5);
                if (!keyValidation(dto.getCountryName())){

                    semaphore.release();

                    result.setResponse(LockResponse.Locked);

                    return result;


                }

                CountryError error = validation.countryValidateOnSave(dto);

                if (!error.isValid()){

                    semaphore.release();

                    result.setResponse(LockResponse.Validation_Failed);
                    result.setMessage("validation failed");
                    result.setDetail(error);

                    return result;

                } else {

                    isActive.put(dto.getCountryName() , true);

                    result.setKey(dto.getCountryName());
                    result.setResponse(LockResponse.TRUE);
                }

                return result;


            } else {
                System.out.println("Could not acquire semaphore");

                result.setResponse(LockResponse.FALSE);

            }

        } catch (InterruptedException e) {

            e.printStackTrace();

            if (permit) {

                isActive.remove(dto.getCountryName());
                semaphore.release();
                System.out.println("Semaphore  e release");
            }

            result.setResponse(LockResponse.Exception);
            result.setMessage("sorry try again");

            return result;
        }

        return result;

    }

    private static boolean keyValidation(String key){

        synchronized (CountryLock.class) {

            return isActive.get(key) == null;
        }
    }

    public static void release(String key){
        semaphore.release();
        isActive.remove(key);
        System.out.println("Semaphore release");
    }
}
