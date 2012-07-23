Projeyi oldugu gibi ekledim. Eger Eclipse jar lari dogru olarak eklemezse,
Projeye sag tiklayip -> Properties -> Java Build Path -> Library Path -> Add Jar -> ProjeKlasoru/lib/lwjgl.jar,slick.jar,mysql-connector-**.jar
Ayni yerde eklenen lwjgl.jar i yandan asagiya indirip native library path secenegini secip edit dedikten sonra ProjeKlasoru/native klasorunu secin.
Bundan sonra programin calismasi lazim ancak database baglantisi olmadan acilmaz. Test modunu kod icinden ayarlamak cok zor o yuzden 
mysql database kopyasini CD nin icine koydum. Bunu mysql workbench ile aldik, buyuk ihtimal mysql-workbench ile load edilirse calisir.
Database adresini src/client/serverconn/QueryExecutor.java daki mysql:// seklinde baslayan adrese yapistirirsaniz olur.
Sorun cikarsa serveri ben bir yerden internete de acabilirim.
st101101001@etu.edu.tr den mail atarsaniz hallederiz.