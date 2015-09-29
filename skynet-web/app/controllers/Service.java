package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Global;
import org.apache.commons.mail.EmailException;
import play.*;
import play.libs.Json;
import play.mvc.*;
import models.SendEmail;
import models.data.EmailBean;

/**
 * Created by fengya on 15-9-29.
 */
public class Service  extends Controller {

    // curl  --request POST
    // --data '{"to": ["xxxx@mogujie.com","xxxx@gmail.com"],"subject":"测试邮件","msg":"测试邮件内容"}'
    // http://127.0.0.1:9000/skynet/service/sendmail
    @BodyParser.Of(BodyParser.Raw.class)
    public Result sendemail() {
        play.mvc.Http.RawBuffer rb = request().body().asRaw();
        if(rb == null){
            return badRequest("Expecting Json data");
        }
        JsonNode json = Json.parse(rb.asBytes());
        if(json == null) {
            return badRequest("Expecting Json data");
        } else {
            EmailBean eb;
            eb = Json.fromJson(json,EmailBean.class);
            try {
                SendEmail.SendEmail(eb.getSubject(),eb.getMsg(),eb.getTo());
                return ok("ok");
            } catch (EmailException e) {
                Global.loger.error("send email error",e);
                return badRequest("Expecting Json data");
            }
        }

    }
}
