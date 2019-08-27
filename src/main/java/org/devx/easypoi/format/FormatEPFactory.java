package org.devx.easypoi.format;

import java.util.HashMap;
import java.util.Map;

public final class FormatEPFactory
{
    private static final Map<Class<? extends FormatEP>, FormatEP> FORMATTERS = new HashMap<>();
    
    static
    {
        FORMATTERS.put(DateTimeEPFormat.class, new DateTimeEPFormat());
        FORMATTERS.put(EnableEPFormat.class, new EnableEPFormat());
        FORMATTERS.put(OnOffEPFormat.class, new OnOffEPFormat());
        FORMATTERS.put(YesNoEPFormat.class, new YesNoEPFormat());
        FORMATTERS.put(YesNoEPFormat.class, new YesNoEPFormat());
        FORMATTERS.put(DecimalEPFormat.class, new DecimalEPFormat());
        FORMATTERS.put(NoneEPFormat.class, new NoneEPFormat());
    }
    
    public static void addFormatter(FormatEP formatter)
    {
        FORMATTERS.put(formatter.getClass(), formatter);
    }
    
    public static FormatEP getFormatter(Class<? extends FormatEP> formatter, boolean onlyValidFormatters)
    {
        FormatEP csvFormatter = FORMATTERS.get(formatter);
        if (csvFormatter == null)
        {
            if (onlyValidFormatters) {
                String msg = "Falha ao encontrar a referência para %. Verifique se o formatter foi adicionado usando FormatEPFactory.addFormatter";
                throw new IllegalArgumentException(String.format(msg, formatter.getClass().getName()));
            } else {
                csvFormatter = new NoneEPFormat();
            }
        }
        return csvFormatter;
    }
    
    
    public static FormatEP getFormatter(Class<? extends FormatEP> formatter) {
        return getFormatter(formatter, true);
    }
    
}
