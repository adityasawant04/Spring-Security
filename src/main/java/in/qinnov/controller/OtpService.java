package in.qinnov.controller;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;



@Service
public class OtpService {

 private static final Integer EXPIRE_MINS = 5;
 Cache<String, Integer> otpCache;
public Logger logger = Logger.getLogger(OtpService.class);
 public OtpService(){
 super();
 otpCache = CacheBuilder.newBuilder().
     expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
      public Integer load(String key) {
             return 0;
       }
   });
 }
//This method is used to push the opt number against Key. Rewrite the OTP if it exists
 //Using user id  as key
 public int generateOTPService(String key){
Random random = new Random();
int otp = 100000 + random.nextInt(900000);
otpCache.put(key, otp);
logger.info(otpCache.toString());
return otp;
 }
 //This method is used to return the OPT number against Key->Key values is username
 public int getOtp(String key){ 
try{
 return otpCache.get(key, new Callable<Integer>() {

	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}); 
}catch (Exception e){
 return 0; 
}
 }
//This method is used to clear the OTP catched already
public void clearOTP(String key){ 
 otpCache.invalidate(key);
 }

public Boolean verifyOtp(String key,String otp)	{
	
	if (getOtp(key)  > 0 )	{
		int otpRetrieve = getOtp(key);
		if ((Integer.parseInt(otp)) == otpRetrieve)	{
			otpCache.invalidate(key);
			return true;
		}
		
		
		
	}
	
	
	return false;
}


}