package org.devx.easypoi.format;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DateTimeEPFormat implements FormatEP
{
    
    private static final String     DEFAULT_FORMAT  = "dd/MM/yyyy HH:mm:ss";
    private static final Set<Class> AUTORIZED_TYPES = new HashSet<>();
    static
    {
        AUTORIZED_TYPES.add(Date.class);
        AUTORIZED_TYPES.add(Calendar.class);
        AUTORIZED_TYPES.add(LocalDate.class);
        AUTORIZED_TYPES.add(LocalDateTime.class);
    }
    
    @Override
    public Set<Class> getAutorizedTypes()
    {
        return AUTORIZED_TYPES;
    }
    
    @Override
    public Object format(String fieldName, Object value, String defaultFormat)
    {
        String res = UNDEFINED;
        if (value == null)
            return res;
        validate(fieldName, value.getClass());
        
        if (defaultFormat == null || defaultFormat.isEmpty())
            defaultFormat = DEFAULT_FORMAT;
        
        DateTimeFormatter temporalFormat = DateTimeFormatter.ofPattern(defaultFormat);
        SimpleDateFormat dateFormat = new SimpleDateFormat(defaultFormat);
        if (value instanceof LocalDate)
        {
            LocalDate date = (LocalDate) value;
            res = date.format(temporalFormat);
        }
        else if (value instanceof LocalDateTime)
        {
            LocalDateTime date = (LocalDateTime) value;
            res = date.format(temporalFormat);
        }
        else if (value instanceof Date)
        {
            Date date = (Date) value;
            res = dateFormat.format(date);
        }
        else if (value instanceof Calendar)
        {
            Calendar calendar = (Calendar) value;
            res = dateFormat.format(calendar.getTime());
        }
        else
        {
            res = UNDEFINED;
        }
        return res;
    }
    
}