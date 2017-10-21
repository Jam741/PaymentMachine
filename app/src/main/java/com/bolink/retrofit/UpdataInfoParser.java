package com.bolink.retrofit;

import com.bolink.bean.UpdateInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

/**
 * Created by xulu on 2017/10/11.
 */

public class UpdataInfoParser {
    /**
     *
     * @param is
     *            解析的xml的inputstream
     * @return updateinfo
     */
    public static UpdateInfo getUpdataInfo(InputStream is) throws Exception {
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        UpdateInfo info = new UpdateInfo();
        parser.setInput(is, "utf-8");
        int type = parser.getEventType();

        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if ("version".equals(parser.getName())) {
                        String version = parser.nextText();
                        info.setVersion(version);
                    } else if ("description".equals(parser.getName())) {
                        String description = parser.nextText();
                        info.setDescription(description);
                    } else if ("apkurl".equals(parser.getName())) {
                        String apkurl = parser.nextText();
                        info.setApkurl(apkurl);
                    } else if ("force".equals(parser.getName())) {
                        String force = parser.nextText();
                        info.setForce(force);
                    } else if ("remind".equals(parser.getName())) {
                        String remind = parser.nextText();
                        info.setRemind(remind);
                    }

                    break;

            }

            type = parser.next();
        }
        is.close();
        return info;
    }
}
