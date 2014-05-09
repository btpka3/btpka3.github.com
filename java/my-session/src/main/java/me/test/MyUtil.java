
package me.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpFields.DateGenerator;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;
//org.eclipse.jetty.http.HttpFields

public class MyUtil extends GenericFilterBean {

    public static boolean quoteIfNeeded(Appendable buf, String s,String delim)
    {
        for (int i=0;i<s.length();i++)
        {
            char c = s.charAt(i);
            if (delim.indexOf(c)>=0)
            {
                quote(buf,s);
                return true;
            }
        }

        try
        {
            buf.append(s);
            return false;
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    // @see org.eclipse.jetty.util.QuotedStringTokenizer#quoteIfNeeded(String, String)
    public static String quoteIfNeeded(String s, String delim)
    {
        if (s==null)
            return null;
        if (s.length()==0)
            return "\"\"";


        for (int i=0;i<s.length();i++)
        {
            char c = s.charAt(i);
            if (c=='\\' || c=='"' || c=='\'' || Character.isWhitespace(c) || delim.indexOf(c)>=0)
            {
                StringBuffer b=new StringBuffer(s.length()+8);
                quote(b,s);
                return b.toString();
            }
        }

        return s;
    }
    private static final char[] escapes = new char[32];
    static
    {
        Arrays.fill(escapes, (char)0xFFFF);
        escapes['\b'] = 'b';
        escapes['\t'] = 't';
        escapes['\n'] = 'n';
        escapes['\f'] = 'f';
        escapes['\r'] = 'r';
    }
 // @see org.eclipse.jetty.util.QuotedStringTokenizer#quote(Appendable, String)
    public static void quote(Appendable buffer, String input)
    {
        try
        {
            buffer.append('"');
            for (int i = 0; i < input.length(); ++i)
            {
                char c = input.charAt(i);
                if (c >= 32)
                {
                    if (c == '"' || c == '\\')
                        buffer.append('\\');
                    buffer.append(c);
                }
                else
                {
                    char escape = escapes[c];
                    if (escape == 0xFFFF)
                    {
                        // Unicode escape
                        buffer.append('\\').append('u').append('0').append('0');
                        if (c < 0x10)
                            buffer.append('0');
                        buffer.append(Integer.toString(c, 16));
                    }
                    else
                    {
                        buffer.append('\\').append(escape);
                    }
                }
            }
            buffer.append('"');
        }
        catch (IOException x)
        {
            throw new RuntimeException(x);
        }
    }

    /* ------------------------------------------------------------ */
    private static class DateGenerator
    {
        private final StringBuilder buf = new StringBuilder(32);
        private final GregorianCalendar gc = new GregorianCalendar(__GMT);

        /**
         * Format HTTP date "EEE, dd MMM yyyy HH:mm:ss 'GMT'"
         */
        public String formatDate(long date)
        {
            buf.setLength(0);
            gc.setTimeInMillis(date);

            int day_of_week = gc.get(Calendar.DAY_OF_WEEK);
            int day_of_month = gc.get(Calendar.DAY_OF_MONTH);
            int month = gc.get(Calendar.MONTH);
            int year = gc.get(Calendar.YEAR);
            int century = year / 100;
            year = year % 100;

            int hours = gc.get(Calendar.HOUR_OF_DAY);
            int minutes = gc.get(Calendar.MINUTE);
            int seconds = gc.get(Calendar.SECOND);

            buf.append(DAYS[day_of_week]);
            buf.append(',');
            buf.append(' ');
            StringUtil.append2digits(buf, day_of_month);

            buf.append(' ');
            buf.append(MONTHS[month]);
            buf.append(' ');
            StringUtil.append2digits(buf, century);
            StringUtil.append2digits(buf, year);

            buf.append(' ');
            StringUtil.append2digits(buf, hours);
            buf.append(':');
            StringUtil.append2digits(buf, minutes);
            buf.append(':');
            StringUtil.append2digits(buf, seconds);
            buf.append(" GMT");
            return buf.toString();
        }

        /* ------------------------------------------------------------ */
        /**
         * Format "EEE, dd-MMM-yy HH:mm:ss 'GMT'" for cookies
         */
        public void formatCookieDate(StringBuilder buf, long date)
        {
            gc.setTimeInMillis(date);

            int day_of_week = gc.get(Calendar.DAY_OF_WEEK);
            int day_of_month = gc.get(Calendar.DAY_OF_MONTH);
            int month = gc.get(Calendar.MONTH);
            int year = gc.get(Calendar.YEAR);
            year = year % 10000;

            int epoch = (int) ((date / 1000) % (60 * 60 * 24));
            int seconds = epoch % 60;
            epoch = epoch / 60;
            int minutes = epoch % 60;
            int hours = epoch / 60;

            buf.append(DAYS[day_of_week]);
            buf.append(',');
            buf.append(' ');
            StringUtil.append2digits(buf, day_of_month);

            buf.append('-');
            buf.append(MONTHS[month]);
            buf.append('-');
            StringUtil.append2digits(buf, year/100);
            StringUtil.append2digits(buf, year%100);

            buf.append(' ');
            StringUtil.append2digits(buf, hours);
            buf.append(':');
            StringUtil.append2digits(buf, minutes);
            buf.append(':');
            StringUtil.append2digits(buf, seconds);
            buf.append(" GMT");
        }
    }

    /* ------------------------------------------------------------ */
    private static final ThreadLocal<DateGenerator> __dateGenerator =new ThreadLocal<DateGenerator>()
    {
        @Override
        protected DateGenerator initialValue()
        {
            return new DateGenerator();
        }
    };

    public static final TimeZone __GMT = TimeZone.getTimeZone("GMT");
    private static final String[] DAYS =
        { "Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        private static final String[] MONTHS =
        { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Jan"};

    public static final String __COOKIE_DELIM="\"\\\n\r\t\f\b%+ ;=";
    public final static String __01Jan1970_COOKIE = formatCookieDate(0).trim();
    public static void formatCookieDate(StringBuilder buf, long date)
    {
        __dateGenerator.get().formatCookieDate(buf,date);
    }
    public static String formatCookieDate(long date)
    {
        StringBuilder buf = new StringBuilder(28);
        formatCookieDate(buf, date);
        return buf.toString();
    }
    public void addSetCookie(
            final String name,
            final String value,
            final String domain,
            final String path,
            final long maxAge,
            final String comment,
            final boolean isSecure,
            final boolean isHttpOnly,
            int version)
    {
        String delim=__COOKIE_DELIM;

        // Check arguments
        if (name == null || name.length() == 0)
            throw new IllegalArgumentException("Bad cookie name");

        // Format value and params
        StringBuilder buf = new StringBuilder(128);
        String name_value_params;
        quoteIfNeeded(buf, name, delim);
        buf.append('=');
        String start=buf.toString();
        if (value != null && value.length() > 0)
            QuotedStringTokenizer.quoteIfNeeded(buf, value, delim);

        if (comment != null && comment.length() > 0)
        {
            buf.append(";Comment=");
            QuotedStringTokenizer.quoteIfNeeded(buf, comment, delim);
        }

        if (path != null && path.length() > 0)
        {
            buf.append(";Path=");
            if (path.trim().startsWith("\""))
                buf.append(path);
            else
                QuotedStringTokenizer.quoteIfNeeded(buf,path,delim);
        }
        if (domain != null && domain.length() > 0)
        {
            buf.append(";Domain=");
            QuotedStringTokenizer.quoteIfNeeded(buf,domain.toLowerCase(),delim);
        }

        if (maxAge >= 0)
        {
            // Always add the expires param as some browsers still don't handle max-age
            buf.append(";Expires=");
            if (maxAge == 0)
                buf.append(__01Jan1970_COOKIE);
            else
                formatCookieDate(buf, System.currentTimeMillis() + 1000L * maxAge);

            if (version >0)
            {
                buf.append(";Max-Age=");
                buf.append(maxAge);
            }
        }

        if (isSecure)
            buf.append(";Secure");
        if (isHttpOnly)
            buf.append(";HttpOnly");

        name_value_params = buf.toString();
        
        HttpServletResponse response;
        response.

        // remove existing set-cookie of same name
        Field field = getField("Set-Cookie");
        Field last=null;
        while (field!=null)
        {
            if (field._value!=null && field._value.toString().startsWith(start))
            {
                _fields.remove(field);
                if (last==null)
                    _names.put(HttpHeaders.SET_COOKIE_BUFFER,field._next);
                else
                    last._next=field._next;
                break;
            }
            last=field;
            field=field._next;
        }

        add(HttpHeaders.SET_COOKIE_BUFFER, new ByteArrayBuffer(name_value_params));

        // Expire responses with set-cookie headers so they do not get cached.
        put(HttpHeaders.EXPIRES_BUFFER, __01Jan1970_BUFFER);
    }
}
