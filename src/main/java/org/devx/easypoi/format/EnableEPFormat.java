package org.devx.easypoi.format;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnableEPFormat implements FormatEP
{
    
    private static final Map<Object, String> FORMATS         = new HashMap<>();
    private static final Set<Class>          AUTORIZED_TYPES = new HashSet<>();
    
    public static final String               ENABLE          = "Ativado(a)";
    public static final String               DISABLE         = "Desativado(a)";
    static
    {
        FORMATS.put("s", ENABLE);
        FORMATS.put("n", DISABLE);
        FORMATS.put("active", ENABLE);
        FORMATS.put("deactive", DISABLE);
        FORMATS.put(Byte.valueOf("1"), ENABLE);
        FORMATS.put(Byte.valueOf("0"), DISABLE);
        FORMATS.put(Short.valueOf("1"), ENABLE);
        FORMATS.put(Short.valueOf("0"), DISABLE);
        FORMATS.put(((Integer) 1), ENABLE);
        FORMATS.put(((Integer) 0), DISABLE);
        FORMATS.put(1D, ENABLE);
        FORMATS.put(0D, DISABLE);
        FORMATS.put(1F, ENABLE);
        FORMATS.put(0F, DISABLE);
        FORMATS.put(true, ENABLE);
        FORMATS.put(false, DISABLE);
        
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
    public Object format(String field, Object value, String defaultFormat)
    {
        String res = UNDEFINED;
        if (value == null)
            return res;
        validate(field, value.getClass());
        
        if (value instanceof String)
            value = value.toString().toLowerCase().trim();
        
        res = FORMATS.get(value);
        if (res == null)
            res = UNDEFINED;
        return res;
    }
    
}