Easy POI - Make XLSX files using POI

# Getting Started

Para utilizar o EasyPOI inicialmente crie um modelo de domínio que represente o documento excel.

# @SheetEP
Para identificar as collections que contenham os dados que serão inseridos nas abas(folhas) da planilha excel utilize a annotation @SheetEP.

Exemplo:

```
  @SheetEP("Detail Bill")
  private List<DetailBill> details;
```

# @ColumnEP
Um vez que tenha identificado as abas(folhas) de sua planilha utilize a annotation @ColumnEP para informar os metadados de uma coluna a ser criada na aba(folha) pelo EasyPOI

Exemplo:
```
  @ColumnEP(value = "Total a pagar", format = DecimalEPFormat.class, pattern="#0.00")
```

| Atributo | Opcional | Descrição                                                                                                                                 | Valor padrão                                                                                                                                                                                                                                                                                                |
|----------|----------|-------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| value    | false    | Header da coluna                                                                                                                          |                                                                                                                                                                                                                                                                                                             |
| format   | true     |  Indentifica o tipo de formação para o valor da coluna.                                                                                   |  NoneEPFormat.class* |
| pattern  | true     | Pattern customizado. Os formats possuem patterns padronizados, entretando caso queira customizar o pattern de formação utilize essa opção |                                                                                                                                                                                                                                                                                                             |

*Outros formats: 
* DateTimeEPFormat para colunas do tipo data/hora 
* DecimalEPFormat para colunas do tipo numérico 
* EnableEPFormat para colunas do tipo 'Ativado' / 'Desativado' 
* OnOffEPFormat  para colunas do tipo 'Ligado' / 'Desligado' 
* YesNoEPFormat  para colunas do tipo 'Sim' / 'Não'

A seguir alguns exemplos de mapeamento de colunas

```
@ColumnEP("Cliente")
private String name;

@ColumnEP("Placa")
private String plate;

@ColumnEP(value = "Velocidade", format = DecimalEPFormat.class)
private Float speed;

@ColumnEP(value = "Data GPS", format = DateTimeEPFormat.class)
private Date evtUtcDt;

@ColumnEP(value = "Data Transmissão", format = DateTimeEPFormat.class)
private Date evtTransDt; 

@ColumnEP(value = "Sinal GPS", format = OnOffEPFormat.class)
private Boolean gpsSignal;

@ColumnEP("Direção em graus") 
private Short direction;

@ColumnEP(value = "GPS Conectado", format = YesNoEPFormat.class)
private Boolean gprsConn;

@ColumnEP(value = "Ignição Ligada", format = YesNoEPFormat.class)
private Boolean ignition;

@ColumnEP(value = "Nível da Bateria em %", format = DecimalEPFormat.class)
private Float powerSupply;

@ColumnEP(value = "Carregando", format = YesNoEPFormat.class)
private Boolean charging;

@ColumnEP(value = "Ancorado", format = YesNoEPFormat.class)
private Boolean anchor;

@ColumnEP("Latitude")
private Float lat;

@ColumnEP("Longitude")
private Float lng;
```

# Exportando Excel
Uma vez que o modelo de domínio esteja mapeado com todos os metadados a classe utilitária EasyPOIExcel disponibilizará duas opções para criação do excel.

1. Utilizar o método build para criar um java.io.ByteArrayOutputStream com a planilha à ser manipulada
2. Utilizar o método buildWorkBook para criar uma instância de org.apache.poi.xssf.usermodel.XSSFWorkbook à ser manipulada

Exemplo: 
```
  Bill bill = repository.getBill(billId);
  ByteArrayOutputStream out = EasyPOIExcel.build(bill)
  // code to export here
```

ou

```
  Bill bill = repository.getBill(billId);
  XSSFWorkbook out = EasyPOIExcel.buildWorkBook(bill)
  // code to export here
```
