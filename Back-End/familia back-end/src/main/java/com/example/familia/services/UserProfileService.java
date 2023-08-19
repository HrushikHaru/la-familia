package com.example.familia.services;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.familia.dtos.UpdateProfileDtoWithPic;
import com.example.familia.dtos.UpdateUserProfileDto;
import com.example.familia.dtos.UserProfileDto;
import com.example.familia.entities.LogIn;
import com.example.familia.entities.UserProfile;
import com.example.familia.repositories.LogInRepository;
import com.example.familia.repositories.UserProfileRepository;
import com.example.familia.responses.UserProfileDetailsResponse;
import org.apache.commons.codec.binary.Base64;

@Service
public class UserProfileService {
	
	private final UserProfileRepository userProfileRepo;
	
	private final LogInRepository logInRepo;
	
	private final JdbcTemplate jdbcTemp;
	
	public UserProfileService(UserProfileRepository userProfileRepo,LogInRepository logInRepo,JdbcTemplate jdbcTemp) {
		this.userProfileRepo = userProfileRepo;
		this.logInRepo = logInRepo;
		this.jdbcTemp = jdbcTemp;
	}
	
	public String uploadImageService(MultipartFile file, int userId, String path) {
		
		String fileName = file.getOriginalFilename();
		
		File f = new File(path+userId);
				
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
			    File outputImageFile = new File(path+userId+"/"+fileName);
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
		
		//Without Optimization
		
//		String name = file.getOriginalFilename();
//		
//		String newName = "profile-pic-"+userId;
//		int indexStart = 0;
//		for(int i=0;i<name.length();i++) {
//			if(name.charAt(i) == '.') {
//				indexStart = i;
//				break;
//			}
//		}
//				
//		newName += name.substring(indexStart, name.length());
//		
//		File f = new File(path+"/"+userId);
//				
//		if(!f.exists()) {
//			f.mkdir();		
//		}
//				
//		String filePath = path+"/"+userId+File.separator+newName;
//		
//		FileOutputStream fileOutput = null;
//		try {
//			byte [] imgBytes = file.getBytes();
//			fileOutput = new FileOutputStream(filePath);
//			fileOutput.write(imgBytes);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				fileOutput.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return newName;
		
	}

	public void uploadUserProfile(UserProfileDto profileDto) {
		
		String profilePicName = profileDto.getProfilePicName();
		String displayName = profileDto.getDisplayName();
		String bio = profileDto.getBio();
		String location = profileDto.getLocation();
		String hobbies = profileDto.getHobbies();
		int userId = profileDto.getUserId();
		
		LogIn logIn = new LogIn();
		logIn.setLogInId(userId);
		UserProfile userProfile = new UserProfile(profilePicName,displayName,bio,location,hobbies,logIn);
		
		userProfileRepo.save(userProfile);
		
	}

	public ResponseEntity<Resource> sendProfileImageToFrontEnd(int userProfileId, String path) {
		 
		 UserProfile validUserProfile = userProfileRepo.findById(userProfileId).get();
		
		 String profileId = userProfileId+"";
		 
		 String fileName = profileId+'/'+validUserProfile.getProfilePicName();
		 String fileName2 = validUserProfile.getProfilePicName();
		 Path imagePath = Paths.get(path + fileName);
		 
	        Resource resource;
	        try {
	            resource = new UrlResource(imagePath.toUri());
	            if (resource.exists() && resource.isReadable()) {
	                return ResponseEntity.ok()
	                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName2 + "\"")
	                        .body(resource);
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	            return ResponseEntity.notFound().build();
	        }
		
	}

	public UserProfileDetailsResponse getUserProfileId(int userId) {
		
		String query = "select * from user_profile a where (select log_in_id from log_in b where a.log_in_id = b.log_in_id) = ?" ;
		
	    Object[] params = {userId};

	    UserProfile userProfile = jdbcTemp.queryForObject(query, params, new BeanPropertyRowMapper<>(UserProfile.class));
	    
	    UserProfileDetailsResponse response = new UserProfileDetailsResponse();
	    
	    response.setUserProfileId(userProfile.getUserProfileId());
	    response.setProfilePicName(userProfile.getProfilePicName());
	    response.setLocation(userProfile.getLocation());
	    response.setDisplayName(userProfile.getDisplayName());
	    response.setBio(userProfile.getBio());
	    response.setHobbies(userProfile.getHobbies());
	    
	    return response;
		
	}

	public List<Map<String, Object>> getAllUsers(int userProfileId, String path) {
		
		List<Map<String, Object>> responseList = new ArrayList<>();
		
		List<UserProfile> allProfiles = userProfileRepo.findAll();
		
		for(UserProfile users:allProfiles) {
			
			if(users.getUserProfileId() != userProfileId) {
				
				byte[] profilePictureData = loadProfilePictureData(users.getUserProfileId(), users.getProfilePicName(), path);
				
				Map<String, Object> userData = new HashMap<>();
				userData.put("userProfileId", users.getUserProfileId());
				userData.put("userName", users.getDisplayName());
				
				//Resource profilePictureResource = new ByteArrayResource(profilePictureData);
				
				String base64ProfilePicture = Base64.encodeBase64String(profilePictureData);
				
				int length = users.getProfilePicName().length();
				String imageExtension = users.getProfilePicName().substring(length-4,length);
				
				HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(getMediaTypeFromExtension(imageExtension));
	            headers.setContentDispositionFormData("attachment", users.getUserProfileId() + imageExtension);
	            
	            Map<String, Object> responseMap = new HashMap<>();
	            responseMap.put("profilePicture", base64ProfilePicture);
	            responseMap.put("userData", userData);
	            
	            responseList.add(responseMap);
				
			}
			
		}
		
		return responseList;
		
	}
	
	private byte[] loadProfilePictureData(int userProfileId, String profilePicName, String path) {
		
		String profileId = userProfileId+"";
		 
		 String fileName = profileId+'/'+profilePicName;
		
        Path imagePath = Paths.get(path+fileName);
        try {
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	private MediaType getMediaTypeFromExtension(String extension) {
        switch (extension.toLowerCase()) {
            case ".jpg":
            case ".jpeg":
                return MediaType.IMAGE_JPEG;
            case ".png":
                return MediaType.IMAGE_PNG;
            case ".gif":
                return MediaType.IMAGE_GIF;
            // Add more cases for other image types if needed
            default:
                return MediaType.APPLICATION_OCTET_STREAM; // Default to binary data
        }
    }
	
	public static BufferedImage createImage(int width, int height, boolean hasAlpha) {
	    return new BufferedImage(width, height, hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
	}
	
	public static BufferedImage removeAlphaChannel(BufferedImage originalImage) {
		BufferedImage target = null;
		if(!originalImage.getColorModel().hasAlpha()) {
			return originalImage; 
		}
		
		target = createImage(originalImage.getWidth(), originalImage.getHeight(), false);
	    Graphics2D g = target.createGraphics();
	    // g.setColor(new Color(color, false));
	    g.fillRect(0, 0, originalImage.getWidth(), originalImage.getHeight());
	    g.drawImage(originalImage, 0, 0, null);
	    g.dispose();
	    
	    return target;
	}

	public void updateUserInfoWithoutPic(UpdateUserProfileDto userProfile) {
		try {
			Optional<UserProfile> validUser = userProfileRepo.findById(userProfile.getUserProfileId());
			
			if(validUser.isPresent()) {
				UserProfile userValid = validUser.get();
				
				userValid.setDisplayName(userProfile.getDisplayName());
				userValid.setHobbies(userProfile.getHobbies());
				userValid.setLocation(userProfile.getLocation());
				userValid.setBio(userProfile.getBio());
				
				userProfileRepo.save(userValid);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public void updateUserInfoWithPic(UpdateProfileDtoWithPic userProfile) {
		try {
			Optional<UserProfile> validUser = userProfileRepo.findById(userProfile.getUserProfileId());
			
			
			if(validUser.isPresent()) {
				UserProfile userValid = validUser.get();
				
				userValid.setDisplayName(userProfile.getDisplayName());
				userValid.setHobbies(userProfile.getHobbies());
				userValid.setLocation(userProfile.getLocation());
				userValid.setBio(userProfile.getBio());
				userValid.setProfilePicName(userProfile.getProfilePicName());
				
				userProfileRepo.save(userValid);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
