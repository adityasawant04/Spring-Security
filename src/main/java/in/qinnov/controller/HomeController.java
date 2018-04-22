package in.qinnov.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import in.qinnov.models.OtpData;
import in.qinnov.models.OtpDataResponse;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	OtpService otpService;

	@Autowired
	MailSender mailSend;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/formUpload", method = RequestMethod.GET)
	public String home() {
		logger.info("Welcome home! The client locale is {}.");



		return "upload";
	}


	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String homeUploadForm(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files) {
		logger.info("Welcome home! The client locale is {}.");

		logger.info("names : " +names.toString() +"fileName : " +files[0].getName());



		return "upload";
	}

	@RequestMapping(value = "/generateOtp", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	public @ResponseBody OtpDataResponse generateOtp(@RequestBody OtpData data) {
		try {
			logger.info("inside generate otp ");
			//OtpData data= new OtpData();

			logger.info("Mobile number : " +data.getMobilenumber());

			int serverOtp = otpService.generateOTPService(data.getMobilenumber());

			logger.info("Mobile Number : " +data.getMobilenumber() +" Otp : " +serverOtp);


			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(data.getMobilenumber());
			mailMessage.setSubject("Otp ");
			mailMessage.setText("Your Otp is : " +serverOtp);
			mailSend.send(mailMessage);



			logger.info("name");

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		OtpDataResponse dataRes = new OtpDataResponse();
		dataRes.setMessage("Successfull");
		return dataRes;
	}

	@RequestMapping(value = "/verifyOtp", method = RequestMethod.POST, consumes="application/json", produces="application/json" )
	public @ResponseBody OtpDataResponse verifyOtp(@RequestBody OtpData data) {
		OtpDataResponse otpDataResponse= new OtpDataResponse();
		try {
			
		
		if (otpService.verifyOtp(data.getMobilenumber(),data.getOtpData()))

		{
			otpDataResponse.setMessage("Your Otp is valid");
			return otpDataResponse;
		}

		otpDataResponse.setMessage("Your otp is invalid");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return otpDataResponse;
	}

}
