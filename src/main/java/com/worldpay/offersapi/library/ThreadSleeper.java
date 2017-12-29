package com.worldpay.offersapi.library;

public class ThreadSleeper {

    public void sleep(
        long sleepDelay
    ) {
        try {
            Thread.sleep(sleepDelay);
        } catch (InterruptedException e) {
            // noop
        }
    }
}
