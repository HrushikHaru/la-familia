package com.example.familia.longPollingUtils;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import com.example.familia.responses.FeedUpdatesResponse;
import com.example.familia.responses.TotalPostsToShow;

public class FeedThread implements Runnable{

	private DeferredResult<FeedUpdatesResponse> result;
	
	private JdbcTemplate jdbcTemp;
	
	private int userProfileId;
	
	private int totalFeeds;
	
	public FeedThread(DeferredResult<FeedUpdatesResponse> result, JdbcTemplate jdbcTemp,int userProfileId, int totalFeeds) {
		this.result = result;
		this.jdbcTemp = jdbcTemp;
		this.userProfileId = userProfileId;
		this.totalFeeds = totalFeeds;
	}

	@Override
	public void run() {
			try {
				Thread.sleep(10000);
				
				String query = "SELECT\r\n"
						+ "    CASE\r\n"
						+ "        WHEN COUNT(*) = ? THEN 'true'\r\n"
						+ "        ELSE 'false'\r\n"
						+ "    END AS is_not_equal_to\r\n"
						+ "FROM user_profile a  \r\n"
						+ "RIGHT JOIN user_profile_following b ON a.user_profile_id = b.user_profile_id\r\n"
						+ "RIGHT JOIN following c ON b.following_id = c.following_id\r\n"
						+ "RIGHT JOIN user_profile d ON c.user_profile_id_following = d.user_profile_id\r\n"
						+ "RIGHT JOIN user_profile_posts e ON d.user_profile_id = e.user_profile_id\r\n"
						+ "WHERE a.user_profile_id = ?\r\n"
						+ "GROUP BY a.user_profile_id;";
				
				
				List<FeedUpdatesResponse> postToShow = jdbcTemp.query(query, ps->{ps.setInt(1, totalFeeds); ps.setInt(2, userProfileId);}, (resultSet,rowNum)->{
				
					String result = resultSet.getString(1);
					
					System.out.println(result);
					
					FeedUpdatesResponse updateResponse = new FeedUpdatesResponse();
					
					updateResponse.setMessage(result);
					
					return updateResponse;
				});
				
				FeedUpdatesResponse totalPosts = postToShow.get(0);
				
				result.setResult(totalPosts);
				
				System.out.println(totalPosts.getMessage());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

}
