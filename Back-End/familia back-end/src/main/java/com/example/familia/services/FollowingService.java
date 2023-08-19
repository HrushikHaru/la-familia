package com.example.familia.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.familia.dtos.ListOfPeopleFollowingDto;

import com.example.familia.repositories.FollowingRepository;
import com.example.familia.responses.FollowingCount;
import com.example.familia.responses.FollowingOtherUsersResponse;

@Service
public class FollowingService {
	
	private final FollowingRepository followingRepo;
	
	private final JdbcTemplate jdbcTemp;
	
	public FollowingService(FollowingRepository followingRepo,JdbcTemplate jdbcTemp) {
		this.followingRepo = followingRepo;
		this.jdbcTemp = jdbcTemp;
	}

	public List<ListOfPeopleFollowingDto> getFollowingList(int userId) {
		
		String query = "select c.following_id , c.following_name, c.user_profile_id_following from user_profile a right join user_profile_following b ON(a.user_profile_id \r\n"
				+ "= b.user_profile_id) right join following c ON(c.following_id = b.following_id) where a.user_profile_id = ?";
		
		List<ListOfPeopleFollowingDto> resultList = jdbcTemp.query(query, ps -> ps.setInt(1, userId), (resultSet, rowNum) -> {
            ListOfPeopleFollowingDto dto = new ListOfPeopleFollowingDto();
            dto.setFollowingId(resultSet.getInt(1));
            dto.setFollowingName(resultSet.getString(2));
            dto.setFollowingPersonUserId(resultSet.getInt(3));
            return dto;
        });
		
		return resultList;
		
	}

	public FollowingCount getFollowingCountByParticularUser(int userId) {
		
		String query = "select count(c.following_id) as followingCount from user_profile a right join user_profile_following b ON(a.user_profile_id \r\n"
				+ "= b.user_profile_id) right join following c ON(c.following_id = b.following_id) where a.user_profile_id = ?";
		
		List<FollowingCount> resultList = jdbcTemp.query(query, ps -> ps.setInt(1, userId), (resultSet, rowNum) -> {
            FollowingCount count = new FollowingCount();
            count.setFollowingCount(resultSet.getInt(1));
            
            return count;
        });
		
		FollowingCount followingCount = new FollowingCount();
		
		for(FollowingCount followCount:resultList) {
			followingCount.setFollowingCount(followCount.getFollowingCount());
		}
		
		return followingCount;
		
	}

	public FollowingOtherUsersResponse checkIfUserFollowsTheOtherUser(int userProfileId, int otherUserId) {
		
		String query = "select exists (select c.user_profile_id_following from user_profile a right join user_profile_following b ON(a.user_profile_id = b.user_profile_id) \r\n"
				+ "right join following c ON(b.following_id = c.following_id) where a.user_profile_id = ? and c.user_profile_id_following = ?)";
		
		int count = jdbcTemp.queryForObject(query, Integer.class, userProfileId, otherUserId);
		
		boolean response = count == 1? true:false;
		
		FollowingOtherUsersResponse followingResponse = new FollowingOtherUsersResponse();
		
		followingResponse.setFollows(response);
		
		Date date = new Date();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(date);
		
		followingResponse.setAskedOn(formattedDate);
		
		return followingResponse;
		
	}

	@Transactional
	public void unfollowTheOtherUserFromList(int userProfileId, int otherUserId) {
		
		//From Following side
		String getFollowingId = "select a.following_id from user_profile_following a right join following b ON(a.following_id = b.following_id) where \r\n"
				+ "a.user_profile_id = ? and b.user_profile_id_following = ?;";
		
		int getFollowingIdNum = jdbcTemp.queryForObject(getFollowingId, Integer.class, userProfileId,otherUserId);
		
		String deleteFromUserFollowing = "delete from user_profile_following where following_id = ?";
		
		jdbcTemp.update(deleteFromUserFollowing, getFollowingIdNum);
		
		String deleteFromFollowing = "delete from following where following_id = ?";
		
		jdbcTemp.update(deleteFromFollowing, getFollowingIdNum);
		
		
		//From Followers side
		String getFollowersId = "select a.followers_id from user_profile_followers a join followers b ON(a.followers_id = b.followers_id) where a.user_profile_id = ?\r\n"
				+ "and b.user_profile_id_follower = ?";
		
		int getFollowersIdNum = jdbcTemp.queryForObject(getFollowersId, Integer.class, otherUserId,userProfileId);
		
		String deleteFromUserFollowers = "delete from user_profile_followers where followers_id = ?";
		
		jdbcTemp.update(deleteFromUserFollowers, getFollowersIdNum);
		
		String deleteFromFollowers = "delete from followers where followers_id = ?";
		
		jdbcTemp.update(deleteFromFollowers, getFollowersIdNum);
		
		
	}

}
