package org.devx.easypoi.format;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class YesNoEPFormat implements FormatEP
{
    
    private static final String              SIM             = "Sim";
    private static final String              NAO             = "Não";
    
    private static final Map<Object, String> FORMATS         = new HashMap<>();
    private static final Set<Class>          AUTORIZED_TYPES = new HashSet<>();
    
    static
    {
        FORMATS.put("s", SIM);
        FORMATS.put("n", NAO);
        FORMATS.put(Byte.valueOf("1"), SIM);
        FORMATS.put(Byte.valueOf("0"), NAO);
        FORMATS.put(Short.valueOf("1"), SIM);
        FORMATS.put(Short.valueOf("0"), NAO);
        FORMATS.put(((Integer) 1), SIM);
        FORMATS.put(((Integer) 0), NAO);
        FORMATS.put(1D, SIM);
        FORMATS.put(0D, NAO);
        FORMATS.put(1F, SIM);
        FORMATS.put(0F, NAO);
        FORMATS.put(true, SIM);
        FORMATS.put(false, NAO);
        
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