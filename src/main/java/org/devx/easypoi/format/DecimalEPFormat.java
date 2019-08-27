package org.devx.easypoi.format;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class DecimalEPFormat implements FormatEP
{
    
    private static final String     DEFAULT_FORMAT  = "#0.00";
    private static final Set<Class> AUTORIZED_TYPES = new HashSet<>();
    static
    {
        AUTORIZED_TYPES.add(Number.class);
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
        
        DecimalFormat formatter = new DecimalFormat(defaultFormat);
        res = formatter.format(((Number)value));
        return res;
    }
    
}