package com.example.familia.services;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.familia.dtos.UserProfilePostsByFollowersDto;
import com.example.familia.dtos.UserProfilePostsDto;
import com.example.familia.entities.UserProfile;
import com.example.familia.entities.UserProfilePosts;
import com.example.familia.repositories.UserProfilePostsRepository;

@Service
public class UserProfilePostsService {
	
	private final UserProfilePostsRepository userProfilePostRepo;
	
	private final JdbcTemplate jdbcTemp;
	
	public UserProfilePostsService(UserProfilePostsRepository userProfilePostRepo,JdbcTemplate jdbcTemp) {
		this.userProfilePostRepo = userProfilePostRepo;
		this.jdbcTemp = jdbcTemp;
	}

	public String addPostImageToFile(MultipartFile file, int userProfileId, String path) {
		
		String fileName = file.getOriginalFilename();
		
		File f = new File(path+userProfileId);
				
		if(!f.exists()) {
			f.mkdir();		
		}
		
		try {
			InputStream inputStream = file.getInputStream();
			
			BufferedImage originalImage = null;
			try {
			    originalImage = ImageIO.read(inputStream);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			
			
			BufferedImage target = UserProfileService.removeAlphaChannel(originalImage);

			try {
			    File outputImageFile = new File(path+userProfileId+"/"+fileName);
			    ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
			    ImageWriteParam param = writer.getDefaultWriteParam();

			    // Set compression quality (0.0 - 1.0), where 1.0 means best quality and 0.0 means highest compression
			    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			    param.setCompressionQuality(0.8f); // You can adjust this value as per your requirements

			    // Create a new image output stream
			    ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputImageFile);
			    writer.setOutput(imageOutputStream);

			    // Write the image with the specified compression quality
			    writer.write(null, new javax.imageio.IIOImage(target, null, null), param);

			    // Close the streams and writer
			    imageOutputStream.close();
			    writer.dispose();
			    
			    
			    return outputImageFile.getName();
			} catch (IOException e) {
			    e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
//		String fileName = file.getOriginalFilename();
//		
//		String imagePath = path+userProfileId+"/"+fileName;
//		
//		//To make the directory, if it doesn't exists
//		File fileMan = new File(path+userProfileId);
//		
//		if(!fileMan.exists()) {
//			fileMan.mkdir();
//		}
//		
//		InputStream inputStream = null;
//		DataInputStream dataInputStream = null;
//		FileOutputStream fileOutputStream = null;
//		DataOutputStream dataOutputStream = null;
//		
//		try {
//			inputStream =((MultipartFile) file).getInputStream();
//			
//			dataInputStream = new DataInputStream(inputStream);
//			
//			byte [] picInBytes = dataInputStream.readAllBytes();
//			
//			fileOutputStream = new FileOutputStream(imagePath);
//			
//			dataOutputStream = new DataOutputStream(fileOutputStream);
//			
//			dataOutputStream.write(picInBytes);
//			
//			dataOutputStream.flush();
//			
//			return fileName;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				dataOutputStream.close();
//				fileOutputStream.close();
//				dataInputStream.close();
//				inputStream.close();	
//				
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return null;
		
	}

	public void addPostByUser(UserProfilePostsDto userPost) {
		
		String postPicName =userPost.getPostPicName();
		String caption = userPost.getCaption();
		String postDateTime = userPost.getPostDateTime();
		
		DateTimeFormatter dateTimeConvert = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss");
		
		String currentDateAndTime = postDateTime.formatted(dateTimeConvert);
		
		int userProfileId = userPost.getUserProfileId();
		
		UserProfile userProfile = new UserProfile();
		userProfile.setUserProfileId(userProfileId);
		
		UserProfilePosts userProfilePosts = new UserProfilePosts();
		
		userProfilePosts.setCaption(caption);
		userProfilePosts.setPostDateTime(currentDateAndTime);
		userProfilePosts.setPostPicName(postPicName);
		userProfilePosts.setUserProfile(userProfile);
		
		userProfilePostRepo.save(userProfilePosts);
		
	}

	public List<UserProfilePostsByFollowersDto> getAllPostsFromFollowers(int userProfileId, String path, String pathProfilePic) {
		
		String query = "SELECT d.user_profile_id, d.display_name, d.profile_pic_name, e.post_id, e.post_pic_name, e.caption, e.post_date_time, d.location, \r\n"
				+ "       (SELECT COUNT(*) FROM post_comments WHERE post_id = e.post_id) AS comment_count,\r\n"
				+ "       (SELECT COUNT(*) FROM like_posts WHERE post_id = e.post_id) AS like_count\r\n"
				+ "FROM user_profile a  \r\n"
				+ "RIGHT JOIN user_profile_following b ON a.user_profile_id = b.user_profile_id\r\n"
				+ "RIGHT JOIN following c ON b.following_id = c.following_id\r\n"
				+ "RIGHT JOIN user_profile d ON c.user_profile_id_following = d.user_profile_id\r\n"
				+ "RIGHT JOIN user_profile_posts e ON d.user_profile_id = e.user_profile_id\r\n"
				+ "WHERE a.user_profile_id = ?\r\n"
				+ "ORDER BY e.post_id DESC;";
		
		List<UserProfilePostsByFollowersDto> listOfPosts=jdbcTemp.query(query, ps -> ps.setInt(1, userProfileId), (resultSet,rowNum)->{
		
			UserProfilePostsByFollowersDto byFollowersDto = new UserProfilePostsByFollowersDto();
			
			byFollowersDto.setUserProfileId(resultSet.getInt(1));
			
			byFollowersDto.setUserProfileDisplayName(resultSet.getString(2));
			
			//Converting profilepic to base64encoding string
			String profilePicName = resultSet.getString(3);
			String base64EncodedImage = base64EncodingOfImages(pathProfilePic, resultSet.getInt(1), profilePicName);
			byFollowersDto.setBase64EncodingProfilePic(base64EncodedImage);
			
			byFollowersDto.setPostId(resultSet.getInt(4));

			//Converting postpic to base64encoding string
			String postPicName = resultSet.getString(5);
			String base64EncodedPostImage = base64EncodingOfImages(path, resultSet.getInt(1), postPicName);
			byFollowersDto.setBase64EncodingPostPic(base64EncodedPostImage);
			
			byFollowersDto.setCaption(resultSet.getString(6));
			
			byFollowersDto.setPostdateTime(resultSet.getTimestamp(7).toString());
			
			byFollowersDto.setLocation(resultSet.getString(8));
			
			byFollowersDto.setCommentCount(resultSet.getInt(9));
			
			byFollowersDto.setLikeCount(resultSet.getInt(10));
			
			return byFollowersDto;
		});
		
		return listOfPosts;
		
	}
	
	public String base64EncodingOfImages(String path, int userProfileId, String profilePicName) {
		
		String imgPathInFile = path+userProfileId+"/"+profilePicName;
		
		Path imgPath = Paths.get(imgPathInFile);
		
		try {
			byte [] imgBytes = Files.readAllBytes(imgPath);
			
			return Base64.encodeBase64String(imgBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

	public List<UserProfilePostsByFollowersDto> getAllPostsFromFollowersPublic(String path, String pathProfilePic) {
		
		String query = "select b.user_profile_id, b.display_name, b.profile_pic_name, a.post_id, a.post_pic_name, a.caption, a.post_date_time, b.location,\r\n"
				+ "(select count(*) from post_comments d where d.post_id = a.post_id) as comment_count, \r\n"
				+ "(select count(*) from like_posts c where c.post_id = a.post_id) as post_count\r\n"
				+ "from user_profile_posts a \r\n"
				+ "left join user_profile b ON(a.user_profile_id = b.user_profile_id) order by a.post_id desc";
		
		
		List<UserProfilePostsByFollowersDto> allPosts = jdbcTemp.query(query, (resultSet,rowNum)->{
			
			UserProfilePostsByFollowersDto byFollowersDto = new UserProfilePostsByFollowersDto();
			
			byFollowersDto.setUserProfileId(resultSet.getInt(1));
			
			byFollowersDto.setUserProfileDisplayName(resultSet.getString(2));
			
			//Converting profilepic to base64encoding string
			String profilePicName = resultSet.getString(3);
			String base64EncodedImage = base64EncodingOfImages(pathProfilePic, resultSet.getInt(1), profilePicName);
			byFollowersDto.setBase64EncodingProfilePic(base64EncodedImage);
			
			byFollowersDto.setPostId(resultSet.getInt(4));

			//Converting postpic to base64encoding string
			String postPicName = resultSet.getString(5);
			String base64EncodedPostImage = base64EncodingOfImages(path, resultSet.getInt(1), postPicName);
			byFollowersDto.setBase64EncodingPostPic(base64EncodedPostImage);
			
			byFollowersDto.setCaption(resultSet.getString(6));
			
			byFollowersDto.setPostdateTime(resultSet.getTimestamp(7).toString());
			
			byFollowersDto.setLocation(resultSet.getString(8));
			
			byFollowersDto.setCommentCount(resultSet.getInt(9));
			
			byFollowersDto.setLikeCount(resultSet.getInt(10));
			
			return byFollowersDto;

		});
		
		return allPosts;
		
	}

}


