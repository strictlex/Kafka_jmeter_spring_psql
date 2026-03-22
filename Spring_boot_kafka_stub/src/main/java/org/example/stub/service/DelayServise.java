package org.example.stub.service;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class DelayServise {
    private final AtomicLong delayMs = new AtomicLong(1000);

    public void setDelay(long delayMs){
        this.delayMs.set(delayMs);
    }

    public long getDelay(){
        return delayMs.get();
    }

    public void sleep() throws InterruptedException{
        long ms = delayMs.get();
        if (ms > 0 ) {
            Thread.sleep(ms);
        }
    }

}
