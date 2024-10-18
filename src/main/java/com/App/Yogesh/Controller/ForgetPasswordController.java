package com.App.Yogesh.Controller;
import com.App.Yogesh.Dto.ChangePassword;
import com.App.Yogesh.Dto.MailBody;
import com.App.Yogesh.Dto.UserDetailsDto;
import com.App.Yogesh.Models.ForgetPassword;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Repository.ForgetPasswordRepository;
import com.App.Yogesh.Repository.UserRepository;
import com.App.Yogesh.Response.ApiResponse;
import com.App.Yogesh.Services.EmailService;
import com.App.Yogesh.Services.UserService;
import com.App.Yogesh.config.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;


@RestController
@RequestMapping("/forgetPassword")
public class ForgetPasswordController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserContext userContext;

    @Autowired
    UserService userService;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgetPasswordRepository forgetPasswordRepository;

    @Autowired
    public ForgetPasswordController(UserRepository userRepository,
                                    EmailService emailService,
                                    ForgetPasswordRepository forgetPasswordRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgetPasswordRepository = forgetPasswordRepository;
    }


    //Get Otp on email for verifying
    @GetMapping("/verifyMail/{email}")
    public ResponseEntity<?> verifyEmail( @PathVariable String email) {
        User user = userRepository.findByEmail(email);
        forgetPasswordRepository.deleteByUserId(user.getId());
        if (user == null) {
            return ResponseEntity.ok(new ApiResponse<>("Email Not Found", HttpStatus.EXPECTATION_FAILED.value(), null));

        }
        int otp = otpGenerator();
        MailBody mailBody = new MailBody(email, "OTP for Password Reset Request", "This is the OTP for your password reset: " + otp);

        ForgetPassword fp = new ForgetPassword(otp, new Date(System.currentTimeMillis() + 5 * 60 * 1000), user);

        emailService.sendSimpleMessage(mailBody);
        forgetPasswordRepository.save(fp);


        return ResponseEntity.ok(new ApiResponse<>("Email sent for Verification", HttpStatus.OK.value(), null));

    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999); // Generates a 6-digit OTP
    }



    //Api for verifying email and otp together
    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.ok(new ApiResponse<>("Email Not Found", HttpStatus.EXPECTATION_FAILED.value(), null));

        }

        Optional<ForgetPassword> optionalFp = forgetPasswordRepository.findByOtpandUser(otp, user);
        if (optionalFp.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>("Unauthorized Otp", HttpStatus.EXPECTATION_FAILED.value(), null));

        }

        ForgetPassword fp = optionalFp.get();
        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgetPasswordRepository.delete(fp); // Delete the expired OTP
            return ResponseEntity.ok(new ApiResponse<>("Otp Expired", HttpStatus.EXPECTATION_FAILED.value(), null));

        }

        // If the OTP is valid and not expired
        return ResponseEntity.ok(new ApiResponse<>("Otp verified Successfully", HttpStatus.OK.value(), null));

    }

 

    //api for changing password
    @PostMapping("/changePassword/{email}")
    public ResponseEntity<?> ChangePassword(@RequestBody ChangePassword changePassword,@PathVariable String email)
    {
        if(!Objects.equals(changePassword.password(),changePassword.repeatedPassword()))
        {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        UserDetailsDto currentUser = userContext.getCurrentUser();
        User user = userService.findUserByEmail(currentUser.getEmail());
        String password=passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(email,password);
          return new ResponseEntity<>(HttpStatus.OK);
    }

}
