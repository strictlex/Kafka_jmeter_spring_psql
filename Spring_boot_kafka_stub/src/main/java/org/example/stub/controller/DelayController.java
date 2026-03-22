package org.example.stub.controller;

import org.example.stub.service.DelayServise;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/delay")
@RequiredArgsConstructor
public class DelayController {

    private final DelayServise delayServise;

    @GetMapping
    public long getDelay() {
        return delayServise.getDelay();
    }

    @PostMapping
    public void setDelay(@RequestParam long ms) {
        delayServise.setDelay(ms);
    }    
}
