package no.mattilsynet.kodeoppgave.java.intervjuoppgavemtjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class StuffController {

    private final StuffService stuffService;

    @Autowired
    public StuffController(StuffService stuffService) {
        this.stuffService = stuffService;
    }

    @PostMapping("/stuff")
    public Map<String, Integer> stuff(
            @RequestBody StuffDto stuff
    ) {
        return stuffService.doStuff(stuff.tekst(), stuff.lengde());
    }

}
