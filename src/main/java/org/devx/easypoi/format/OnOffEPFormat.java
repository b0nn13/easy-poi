package org.devx.easypoi.format;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OnOffEPFormat implements FormatEP
{
    
    private static final Map<Object, String> FORMATS         = new HashMap<>();
    private static final Set<Class>          AUTORIZED_TYPES = new HashSet<>();
    
    public static final String               ON              = "Ligado";
    public static final String               OFF             = "Desligado";
    static
    {
        FORMATS.put("s", ON);
        FORMATS.put("n", OFF);
        FORMATS.put(Short.valueOf("1"), ON);
        FORMATS.put(Short.valueOf("0"), OFF);
        FORMATS.put(Byte.valueOf("1"), ON);
        FORMATS.put(Byte.valueOf("0"), OFF);
        FORMATS.put(((Integer) 1), ON);
        FORMATS.put(((Integer) 0), OFF);
        FORMATS.put(1D, ON);
        FORMATS.put(0D, OFF);
        FORMATS.put(1F, ON);
        FORMATS.put(0F, OFF);
        FORMATS.put(true, ON);
        FORMATS.put(false, OFF);
        
        AUTORIZED_TYPES.add(Number.class);
        AUTORIZED_TYPES.add(String.class);
        AUTORIZED_TYPES.add(Boolean.class);
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
        
        if (value instanceof String)
            value = value.toString().toLowerCase().trim();
        
        res = FORMATS.get(value);
        if (res == null)
            res = UNDEFINED;
        return res;
    }
    
}