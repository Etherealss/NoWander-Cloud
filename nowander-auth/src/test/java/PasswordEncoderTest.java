import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wtk
 * @date 2022-10-17
 */
public class PasswordEncoderTest {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String[] s = new String[] {
                "8fWVLR4EVqz7QMuowTlvUPPwDKxut!xN",
        };

        for (String s1 : s) {
            System.out.println(passwordEncoder.encode(s1));
        }
    }
}
/*



 */