package com.example.familia.services;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.familia.dtos.GetDetailsForUserDeatilsPost;
import com.example.familia.dtos.UserDetailsDto;

@Service
public class UserProfileDetailsService {
	
	private final JdbcTemplate jdbcTemp;
	
	private final UserProfilePostsService userProfileServ;
	
	public UserProfileDetailsService(JdbcTemplate jdbcTemp, UserProfilePostsService userProfileServ) {
		this.jdbcTemp = jdbcTemp;
		this.userProfileServ = userProfileServ;
	}

	public UserDetailsDto getAllDetails(int userProfileId) {
		
		String query = "SELECT\r\n"
				+ "    z.display_name,\r\n"
				+ "    z.hobbies,\r\n"
				+ "    z.location,\r\n"
				+ "    z.bio,\r\n"
				+ "    (\r\n"
				+ "        SELECT COUNT(*)\r\n"
				+ "        FROM user_profile a\r\n"
				+ "        RIGHT JOIN user_profile_following b ON (a.user_profile_id = b.user_profile_id)\r\n"
				+ "        RIGHT JOIN following c ON (b.following_id = c.following_id)\r\n"
				+ "        WHERE a.user_profile_id = z.user_profile_id\r\n"
				+ "    ) AS following,\r\n"
				+ "    (\r\n"
				+ "        SELECT COUNT(*)\r\n"
				+ "        FROM user_profile a\r\n"
				+ "        RIGHT JOIN user_profile_followers b ON (a.user_profile_id = b.user_profile_id)\r\n"
				+ "        RIGHT JOIN followers c ON (b.followers_id = c.followers_id)\r\n"
				+ "        WHERE a.user_profile_id = z.user_profile_id\r\n"
				+ "    ) AS followers,\r\n"
				+ "    (\r\n"
				+ "        SELECT COUNT(*)\r\n"
				+ "        FROM user_profile_posts\r\n"
				+ "        WHERE user_profile_id = z.user_profile_id\r\n"
				+ "    ) AS posts\r\n"
				+ "FROM user_profile z\r\n"
				+ "WHERE z.user_profile_id = ?";
		
		List<UserDetailsDto> result = jdbcTemp.query(query, ps->ps.setInt(1, userProfileId), (resultSet,rowNum)->{
			
			UserDetailsDto userDetails = new UserDetailsDto();
			userDetails.setDisplayName(resultSet.getString(1));
			
			userDetails.setHobbies(resultSet.getString(2));
			
			userDetails.setLocation(resultSet.getString(3));
			
			userDetails.setBio(resultSet.getString(4));
			
			userDetails.setFollowing(resultSet.getInt(5));
			
			userDetails.setFollowers(resultSet.getInt(6));
			
			userDetails.setPosts(resultSet.getInt(7));
			
			return userDetails;
		});
		
		System.out.println(result.get(0));
		
		return result.get(0);
		
	}

	public List<GetDetailsForUserDeatilsPost> getAllPostsOfTheUser(int userProfileId, String imgPath) {
		
		String query = "select a.caption, a.post_pic_name, a.post_id,\r\n"
				+ "(select count(*) from like_posts where post_id = a.post_id) as likeCount,\r\n"
				+ "(select count(*) from post_comments where post_id = a.post_id) as commentCount\r\n"
				+ "from user_profile_posts a where a.user_profile_id = ? order by post_id desc";
		
		List<GetDetailsForUserDeatilsPost> listOfPosts = jdbcTemp.query(query, ps->ps.setInt(1, userProfileId), (resultSet,rowNum)->{
			
			GetDetailsForUserDeatilsPost getData = new GetDetailsForUserDeatilsPost();
			
			String base64EncodedImage = userProfileServ.base64EncodingOfImages(imgPath, userProfileId, resultSet.getString(2));
			
			getData.setBase64EncodedPostPic(base64EncodedImage);
			
			getData.setCaption(resultSet.getString(1));
			
			getData.setLikeCount(resultSet.getInt(4));
			
			getData.setCommentCount(resultSet.getInt(5));
			
			return getData;
		});
		
		return listOfPosts;
		
	}

}
