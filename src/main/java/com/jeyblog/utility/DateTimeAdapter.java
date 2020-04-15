package com.jeyblog.utility;


import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//https://javarevisited.blogspot.com/2017/04/jaxb-date-format-example-using-annotation-XMLAdapter.html
public class DateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    private final DateTimeFormatter dateFormat =  DateTimeFormatter.ISO_DATE_TIME;
        @Override public LocalDateTime unmarshal(String xml) throws Exception {
            return (LocalDateTime) dateFormat.parse(xml);
        }
        @Override
        public String marshal(LocalDateTime object) throws Exception { return dateFormat.format(object);
        }
}

//    Read more: https://javarevisited.blogspot.com/2017/04/jaxb-date-format-example-using-annotation-XMLAdapter.html#ixzz6JcZiKUOh
