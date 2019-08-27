package org.devx.easypoi.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SheetEP Annotation utilizada para informar os metadados de uma aba de EXCEL a ser criada pelo EasyPoi
 * Exemplo: @SheetEP("Faturamento"), nesse caso será criada uma aba chama Faturamento na planilha excel.
 * Para mapear os metadados das colunas verifique {@link ColumnEP}
 * @author fabio.xavier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface SheetEP
{
    /**
     * Nome da aba
     */
    String value();
}
