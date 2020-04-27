## FunWithDates 

Es gibt viele verschiedene Klassen in Java um ein Datum zu repräsentieren:

* java.util.Date: hat lokale Zeitzone
* java.util.Instant: hat immer UTC 
* java.util.LocalDate: zum Initalisieren wird die lokale Zeitzone gespeichert
* java.util.LocalDateTime: zum Initalisieren wird die lokale Zeitzone gespeichert

java.util.Date lässt sich gut verwenden, wenn die Zeitzone wichtig ist. 
Wenn du auf die Zeitzone verzichten kannst, ist es besonders für Speicherung, Serialisierung und Deserialisierung am Einfachsten, wenn du die Instant Klasse verwendest.
LocalDate hat praktische Konstruktoren um basierend auf einem Jahr, Monat und Tag ein Datum zu erstellen. Wird auch die Uhrzeit gebraucht 
empfiehlt sich LocalDateTime. LocalDate besitzt praktische Funktionen zum Addieren von Tagen und zum Konvertieren in ein LocalDateTime wie `atStartOfDay` und `atEndOfDay`. 
Diese sind besonders hilfreich, wenn man zum Beispiel nur in einem bestimmten Zeitrahmen eine Daten aus einer Datenbank anzeigen möchte. 

### Formattierungen

Um ein Datum zu formattieren oder aus einem String in einem bestimmten Format einzulesen eignet sich das [SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html) gut. 

### Epoch Timestamp

Oft fällt man über die Bezeichnungen EpochSeconds, EpochDays, EpochMillis oder ähnliches. Dies sind elegante Wege mit einer einzigen Zahl einen Zeitstempel abzubilden. 
Gezählt werden dabei die Sekunden/Tage/Millisekunden/Nanosekunden seit dem 1. Jänner 1970. 
Das heißt ein Timestamp mit EpochSeconds von 3600 bzw EpochMillis von 3600 000 entspricht dem 1.Jänner 1970 01:00. 
EpochSeconds von 1587356400 bzw EpochMillis von 1587356400000 entsprechen dem 20.04.2020 04:20.

# FunWithDatabases 

Im Konstruktor des TeacherDAOs laden wir die Treiber Klasse die es uns ermöglicht, mit der Datenbank zu sprechen. 
Damit dieser Code bei dir lokal funktioniert musst du das [jdbc-connector.jar](https://mvnrepository.com/artifact/mysql/mysql-connector-java/5.1.13) herunterladen und unter den Libraries hinzufügen. 

Als nächstes öffnen wir eine Connection mittels der Hilfsmethode `openConnection` im TeacherDAO. Mit dieser Connection können wir dann ein prepared statement erstellen.
Ein prepared statement erstellt man durch Übergabe eines Strings, der gültige SQL Syntax enthalten muss. Fragezeichen stehen dabei für Parameter, die danach mit den set-Methoden gesetzt werden können. 
Achtung hier wird bei 1 zu zählen begonnen! Es ist wichtig hier Parameter zu verwenden anstelle diese direkt im übergebenen String zu konkatinieren um SQL Injections zu vermeiden. Denn die Parameter die übergeben werden, werden escaped, sodass hier kein 
Blödsinn gemacht werden kann. 

Handelt es sich um einen lesenden Zugriff dann wird auf dem prepared Statement `executeQuery()` aufgerufen. Dabei wird ein ResultSet returniert. 
Ein ResultSet ist ein Cursor der auf die Zeilen im Ergebnis der SQL-Query zeigt. Initial zeigt der Cursor auf die Zeile -1, also vor die erste Zeile. 
Die Methode `next()` auf dem ResultSet lässt den Cursor in die nächste Zeile springen und returniert einen boolean, der ausdrückt ob die Zeile noch gültig ist und Daten enthält oder ob wir schon alle Zeilen gelesen haben. 
Mit verschiedenen getter Methoden können wir auf Spalten der aktuell im ResultSet repräsentierten Datenzeile zugreifen - entweder Index-basiert oder über den Spaltennamen in der Datenbank. 
Mit `resultSet.getInt(3)` holen wir uns den Wert der dritten Spalte als Integer, mit `resultSet.getString('first_name')` holen wir uns den Wert der in der Spalte
first_name steht als String.

Handelt es sich um einen schreibenden Zugriff auf die Datenbank wird auf dem prepared Statement `execute()` ausgeführt. 
Execute liefert ein boolsches Flag zurückt, das ausdrückt welchen Rückgabewert die SQL Query hatte. Ist der Rückgabewert von `execute()` true, dann 
lieferte die SQL Query ein ResultSet, welches wir mit `preparedStatement.getResultSet()` auslesen können. Ist der Rückagabewert von `execute()` false, dann 
lieferte die SQL Query kein Resultat oder einen update count. Dieser drückt aus wieviele Zeilen von der Query betroffen waren und kann mit `preparedStatement.getUpdateCount()` ausgelesen werden.

