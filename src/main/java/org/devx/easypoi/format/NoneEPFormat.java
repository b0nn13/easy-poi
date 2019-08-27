package org.devx.easypoi.format;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NoneEPFormat implements FormatEP
{
    
    private static final String              SIM             = "Sim";
    private static final String              NAO             = "Não";
    
    private static final Map<Object, String> FORMATS         = new HashMap<>();
    private static final Set<Class>          AUTORIZED_TYPES = new HashSet<>();
    
    
    @Override
    public Set<Class> getAutorizedTypes()
    {
        return AUTORIZED_TYPES;
    }
    
    @Override
    public Object format(String field, Object value, String defaultFormat)
    {
        return value;
    }
    
}