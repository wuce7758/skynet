package models;

import play.Configuration;
import play.Logger;
import play.Play;

/**
 * Created by fengya on 15-9-29.
 */
public class Global{
    public static final Logger.ALogger loger =  Logger.of("GLOBAL");
    public static String urlprefix =  "";

    static {
        Configuration config = Play.application().configuration();
        urlprefix = config.getString("custom.urlprefix", "");
    }
}
