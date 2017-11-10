package com.bolink.retrofit;

import com.bolink.bean.DataBaseInfo;
import com.bolink.bean.UpdateInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

/**
 * Created by xulu on 2017/10/11.
 */

public class UpdataInfoParser {
    /**
     * @param is 解析的xml的inputstream
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

    public static DataBaseInfo getDataBaseInfo(InputStream is) throws Exception {
        XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
        DataBaseInfo dataBaseInfo = new DataBaseInfo();
        xmlPullParser.setInput(is, "UTF-8");
        int type = xmlPullParser.getEventType();

        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if ("dbname".equals(xmlPullParser.getName())) {
                        dataBaseInfo.setDbname(xmlPullParser.getAttributeValue(null, "value"));
                    } else if ("version".equals(xmlPullParser.getName())) {
                        dataBaseInfo.setVersion(xmlPullParser.getAttributeValue(null, "value"));
                    } else if ("list".equals(xmlPullParser.getName())) {
//                        dataBaseInfo.setDbname(xmlPullParser.getAttributeValue(null, "value"));
                    } else if ("storage".equals(xmlPullParser.getName())) {
                        dataBaseInfo.setStorage(xmlPullParser.getAttributeValue(null, "value"));
                    }
            }
            type = xmlPullParser.next();
        }
        is.close();
        return dataBaseInfo;
    }


}
