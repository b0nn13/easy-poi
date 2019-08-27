package org.devx.easypoi.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.devx.easypoi.format.DateTimeEPFormat;
import org.devx.easypoi.format.DecimalEPFormat;
import org.devx.easypoi.format.EnableEPFormat;
import org.devx.easypoi.format.FormatEP;
import org.devx.easypoi.format.NoneEPFormat;
import org.devx.easypoi.format.OnOffEPFormat;
import org.devx.easypoi.format.YesNoEPFormat;


/**
 * Annotation utilizada para informar os metadados de uma coluna a ser criada pelo EasyPoi
 * Exemplo: @ColumnEP(value = "Total a pagar", format = DecimalEPFormat.class, pattern="#0.00")
 * @author fabio.xavier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface ColumnEP
{
    /**
     * Nome da coluna
     */
    String value();
    
    /**
     * Identifica o tipo de formação para o valor da coluna.
     * Opções disponíveis:
     * <li> {@link DateTimeEPFormat} para colunas do tipo <b>data/hora</b>
     * <li> {@link DecimalEPFormat} para colunas do tipo <b>numérico</b>
     * <li> {@link EnableEPFormat} para colunas do tipo <b>'Ativado' / 'Desativado'</b>
     * <li> {@link OnOffEPFormat}  para colunas do tipo <b>'Ligado' / 'Desligado' </b>
     * <li> {@link YesNoEPFormat}  para colunas do tipo <b>'Sim' / 'Não'</b>
     * <li> {@link NoneEPFormat} para colunas <b>sem formatação</b>
     * <br>Caso necessite utilizar um <b>formato customizado</b> crie um 
     * novo formater implementando a interface {@link FormatEP} 
     */
    Class<? extends FormatEP> format() default NoneEPFormat.class;
    /**
     * Pattern customizado. Os formats possuem patterns padronizados, entretando caso
     * queira customizar o pattern de formação utilize essa opção 
     */
    String pattern() default "";
}
