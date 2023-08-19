package com.example.familia.updatesSection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.familia.responses.TotalPostsToShow;

@RestController
@RequestMapping("/updates")
public class UpdatesController {
	
	private final Map<Integer, CopyOnWriteArrayList<SseEmitter>> emittersMap = new ConcurrentHashMap<>();
	
    @GetMapping(path = "/feed-updates/{userProfileId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeUpdates(@PathVariable int userProfileId) {
        SseEmitter emitter = new SseEmitter();
        emittersMap.putIfAbsent(userProfileId, new CopyOnWriteArrayList<>());
        emittersMap.get(userProfileId).add(emitter);

        emitter.onCompletion(() -> {
            emittersMap.get(userProfileId).remove(emitter);
            if (emittersMap.get(userProfileId).isEmpty()) {
                emittersMap.remove(userProfileId);
            }
        });

        emitter.onTimeout(() -> {
            emittersMap.get(userProfileId).remove(emitter);
            if (emittersMap.get(userProfileId).isEmpty()) {
                emittersMap.remove(userProfileId);
            }
        });

        System.out.println(emitter);

        return emitter;
    }
    
    public void sendUpdateToAllClients(TotalPostsToShow totalPostsToShow, int userProfileId) {
    	if (emittersMap.containsKey(userProfileId)) {
            emittersMap.get(userProfileId).forEach(emitter -> {
                try {
                    emitter.send(totalPostsToShow);
                } catch (Exception e) {
                    // Handle any exceptions if necessary
                	e.printStackTrace();
                }
            });
        }

    }


}
