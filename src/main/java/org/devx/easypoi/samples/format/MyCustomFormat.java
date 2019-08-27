package org.devx.easypoi.samples.format;

import java.util.HashSet;
import java.util.Set;

import org.devx.easypoi.format.FormatEP;
import org.devx.easypoi.format.FormatEPFactory;

public class MyCustomFormat implements FormatEP
{
    
    @Override
    public Object format(String fieldName, Object value, String defaultFormat)
    {
        validate(fieldName, value.getClass());
        return value + " formated";
    }
    
    @Override
    public Set<Class> getAutorizedTypes()
    {
        Set<Class> autorizedTypes = new HashSet<>();
        autorizedTypes.add(Number.class);
        return autorizedTypes;
    }
    
}
