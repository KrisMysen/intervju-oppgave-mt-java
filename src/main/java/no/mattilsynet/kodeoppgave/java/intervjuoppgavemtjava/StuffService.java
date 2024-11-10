package no.mattilsynet.kodeoppgave.java.intervjuoppgavemtjava;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class StuffService {

    public Map<String, Integer> doStuff(String tekst, int lengde) {
        var words = tekst.split(" ");
        var processedWords = new HashMap<String, Integer>();

        Arrays.stream(words).forEach(word -> {
            if (word.length() == lengde) {
                processedWords.put(word, (int) Arrays.stream(words).filter(w -> w.equals(word)).count());
            }
        });

        return processedWords;
    }
}
