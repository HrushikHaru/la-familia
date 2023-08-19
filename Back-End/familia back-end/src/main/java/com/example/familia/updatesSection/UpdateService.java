package com.example.familia.updatesSection;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.familia.responses.TotalPostsToShow;

@Service
public class UpdateService {

	private final UpdatesController updatesController;
	
	private JdbcTemplate jdbcTemp;

	public UpdateService(UpdatesController updatesController, JdbcTemplate jdbcTemp) {
	    this.updatesController = updatesController;
	    this.jdbcTemp = jdbcTemp;
	}

	public void performUpdate(int userProfileId) {
	   // Your logic to update data here
		String query = "select count(*) from user_profile a  \r\n"
				+ "right join user_profile_following b ON(a.user_profile_id =\r\n"
				+ "b.user_profile_id) right join following c ON(b.following_id = c.following_id) right join user_profile d \r\n"
				+ "ON(c.user_profile_id_following = d.user_profile_id) right join user_profile_posts e ON(d.user_profile_id = e.user_profile_id)\r\n"
				+ "where a.user_profile_id = ? order by e.post_id asc";
		
		List<TotalPostsToShow> postToShow = jdbcTemp.query(query, ps->ps.setInt(1, userProfileId), (resultSet,rowNum)->{
			
			TotalPostsToShow posts = new TotalPostsToShow();
			posts.setPostsNumber(resultSet.getInt(1));
			
			return posts;
		});
		
		TotalPostsToShow totalPosts = postToShow.get(0);
	   
	   updatesController.sendUpdateToAllClients(totalPosts, userProfileId);
	}
}



