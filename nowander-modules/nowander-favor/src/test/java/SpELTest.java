import com.nowander.favor.infrastructure.enums.FavorTargetType;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author wtk
 * @date 2022-10-13
 */
public class SpELTest {
    public String name = "abc";
    public static void main(String[] args) {
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("targetType", FavorTargetType.ARTICLE);
//        String exp = "#targetType.code";
        String exp = "':' + #targetType.code + ':'";
//        String exp = "'qewqeq'";
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(exp);
        String value = expression.getValue(context, String.class);
        System.out.println(value);
    }
}
