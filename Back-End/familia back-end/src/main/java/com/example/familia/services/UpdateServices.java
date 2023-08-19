package com.example.familia.services;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.familia.dtos.UserProfilePostsByFollowersDto;

@Service
public class UpdateServices {

	private final JdbcTemplate jdbcTemp;
	
	private final UserProfilePostsService userProfileServ;
	
	public UpdateServices(JdbcTemplate jdbcTemp, UserProfilePostsService userProfileServ) {
		this.jdbcTemp = jdbcTemp;
		this.userProfileServ = userProfileServ;
	}

	public List<UserProfilePostsByFollowersDto> getFeedUpdates(int userProfileId, int offSet, String path, String pathProfilePic) {
		
		String query = "select d.user_profile_id, d.display_name, d.profile_pic_name, e.post_id, e.post_pic_name, e.caption, e.post_date_time, d.location from user_profile a  \r\n"
				+ "right join user_profile_following b ON(a.user_profile_id =\r\n"
				+ "b.user_profile_id) right join following c ON(b.following_id = c.following_id) right join user_profile d \r\n"
				+ "ON(c.user_profile_id_following = d.user_profile_id) right join user_profile_posts e ON(d.user_profile_id = e.user_profile_id)\r\n"
				+ "where a.user_profile_id = ? order by e.post_id asc limit ?,18446744073709551615";
		
		List<UserProfilePostsByFollowersDto> postToShow = jdbcTemp.query(query, ps->{ps.setInt(1, userProfileId); ps.setInt(2, offSet);}, (resultSet,rowNum)->{
			
			UserProfilePostsByFollowersDto byFollowersDto = new UserProfilePostsByFollowersDto();
			
			byFollowersDto.setUserProfileId(resultSet.getInt(1));
			
			byFollowersDto.setUserProfileDisplayName(resultSet.getString(2));
			
			//Converting profilepic to base64encoding string
			String profilePicName = resultSet.getString(3);
			String base64EncodedImage = userProfileServ.base64EncodingOfImages(pathProfilePic, resultSet.getInt(1), profilePicName);
			byFollowersDto.setBase64EncodingProfilePic(base64EncodedImage);
			
			byFollowersDto.setPostId(resultSet.getInt(4));

			//Converting postpic to base64encoding string
			String postPicName = resultSet.getString(5);
			String base64EncodedPostImage = userProfileServ.base64EncodingOfImages(path, resultSet.getInt(1), postPicName);
			byFollowersDto.setBase64EncodingPostPic(base64EncodedPostImage);
			
			byFollowersDto.setCaption(resultSet.getString(6));
			
			byFollowersDto.setPostdateTime(resultSet.getTimestamp(7).toString());
			
			byFollowersDto.setLocation(resultSet.getString(8));
			
			return byFollowersDto;

		});
		
		return postToShow;
	}
	
}
