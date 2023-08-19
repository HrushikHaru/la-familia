package com.example.familia.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.example.familia.longPollingUtils.FeedThread;
import com.example.familia.responses.FeedUpdatesResponse;
import com.example.familia.responses.TotalPostsToShow;

@RestController
@RequestMapping(path = "/long-polling")
public class LongPollingController {
	
	private final JdbcTemplate jdbcTemp;
	
	public LongPollingController(JdbcTemplate jdbcTemp) {
		this.jdbcTemp = jdbcTemp;
	}
	
	@GetMapping(path = "/update-feeds/{userProfileId}/{totalFeeds}")
	public DeferredResult<FeedUpdatesResponse> GetUpdateOnFeeds(@PathVariable int userProfileId, @PathVariable int totalFeeds) {
		
		DeferredResult<FeedUpdatesResponse> result = new DeferredResult<>();
		
		FeedThread feedThread = new FeedThread(result, jdbcTemp, userProfileId, totalFeeds);
		
		Thread feedThreadToRun = new Thread(feedThread);
		
		feedThreadToRun.start();
		
		return result;
		
	}

}
