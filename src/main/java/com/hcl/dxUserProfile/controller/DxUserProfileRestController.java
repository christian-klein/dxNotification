package com.hcl.dxUserProfile.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.userprofile.model.DxUserProfile;

@RestController
@RequestMapping("/api")
public class DxUserProfileRestController {

	@GetMapping("/userProfile/{userId}")
	@Cacheable("userProfile")
	
	public DxUserProfile getDxUserProfile(@PathVariable String userId, String siteId) {
		return this.createDxUserProfile(userId);
		
	}
	
	public DxUserProfile createDxUserProfile(String userId) {
		try {
			//this is to simulate the delay going to the backend services to retrieve the actual user profiles. Adding this for the purpose of demonstrating caching
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DxUserProfile userProfile = new DxUserProfile(userId, "John", "Smith", "John.Smith@hcl.com", "John.Smith", "123456789");
		return userProfile;
	}

}
