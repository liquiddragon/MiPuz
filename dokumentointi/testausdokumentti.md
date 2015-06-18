Testaus:
========

Testauksessa on keskitytty logiikan ja siihen liittyvän toiminnallisuuden testaamiseen. Myös yksityisiä metodeja on testattu ihan oman mielenkiinnon takia ja oppiakseni reflection käyttöä. Mutaatiota on myös poistettu paljon joskin osan kanssa on tehty paljon työtä. Jäljellä olevista muutama, mm. pelien kuorien suorituksen käynnistävä tai pelikäyttöliittymän poisto, on jätetty sillä niiden poistaminen menisi osin jo käyttöliittymän testaukseen. Pientä käyttöliitymään liittyvää testausta löytyy XXGameTest luokista siinä että siellä korvataan pelin paneeli testiluokan tarjoamalla tyhjällä paneelilla jonka esim. elementtien lukumäärää tarkastetaan.

MMGameTest luokan kohdalla on NetBeans tai Maven osalla jotain ongelmia sillä mmgame.ui.* rivi ja sitä käyttävät rivit NetBeans merkitsee punaisella ja kertoo että 'Java EE API is missing on project classpath' vaikka mitään tähän liittyvää ei ole käytössä. Ainoa [vinkki](http://stackoverflow.com/questions/7603373/java-ee-api-is-missing-on-project-classpath-while-using-httpunit-for-servlet-tes/7604091#7604091) mikä tuntui löytyvän tähän liittyen oli riippuvuuksien järjestys pom.xml tiedostossa, mutta k.o. projekti käytti Java EE:ta mitä MiPuz ei tee.

Automaattisten testien ulkopuolelle on jäänyt lähinnä käyttöliittymät ja niihin liittyvät toiminnallisuudet sekä mm. kolmansienpuolien kirjastot ja apurutiinit.

Kirjastoja ja apuruutiineja on testattu kehityksen aikana mm. kokeilemalla niiden toimintaa erilaisilla arvoilla. Esimerkiksi framework.utilities paketin GraphicsUtil scaleImageIcon metodia tuli testattua eri kuvilla ja skaalausarvoilla. Saman paketin RelativeLayout tuli kokeiltua eri tavoin käyttöliittymän kehityksen aikana.

Kehyksen käyttöliittymää tuli testattua ja koestettua melko alusta kun se oli luotu läpi koko projektin.

Pelien käyttöliittymiä ja niihin liittyviä toiminnallisuuksia kuten viestejä (event) ja ajastimia (timer) testattiin alussa ilman pelilogiikka niin pitkälle kun pystyi. Esimerkiksi XXStateEvent viestejä varten tuli luotua peruskäyttöliittymä, lisättyä viestit ja kokeiltua niiden toimintaa ennen varsinaisen muun osuuden tuontia kuvaan. Näin pystyi havaitsemaan niiden toimivuuden ja toiminnan koko käyttöliittymän kehityksen ajan. Ajastimien suhteen alkutestaus oli ongelmatonta, mutta loppua kohden kun pelilogiikka oli tuotu mukaan sitä joutui muuttamaan sillä alkuperäinen aiheutti ongelmia mm. pelin valintojen näkyvyydessä käyttäjälle. Tässä huomasi että tietyt asiat eivät tule esille alussa samalla tavoin kuin vaikka viestien suhteen jossa alkuperäinen toiminta pysyi kunnossa läpi kehityksen.

Pelien toiminnallisuutta piti testata usealla pelillä varsinkin MMGame kohdalla. BBGame:n käyttöliittymälogiikka oli yksinkertaisempi ja suoraviivaisempi joten sen toiminnan sai kuntoon huomattavasti kevyemmällä testauksella kuin MMGame:n.

*BBGame*: Testauksessa ongelmallisin oli suurien, 32-bittisten, arvojen käsittely joka tuntui aiheuttavan ongelmia siten että Java tulkitsi arvot negatiivisiksi vaikka muuttujien arvoalueissa ei ollut ongelmaa. Lopulta ongelman syyksi paljastui vakioarvot jotka ilman `L` päätettä tuli tulkituksi `int` tyyppiseksi `long` sijaan.

*MMGame*: Testauksen ongelmallisin kohta oli saada käyttäjälle näytettyjen valintojen toiminta varmaksi ja vakaaksi. Jostain syystä viestit ja ajastimet eivät tuntuneet toimivan aivan oletusten & dokumentaation sekä muiden käyttäjien selityksen mukaan ja vielä nytkin valmiina olevassa ohjelmassa saattaa joskus käydä siten että jokin yksittäinen valinta jollain kerralla on lyhyempi kuin muut tai toisella kerralla näytettävä. Varsinaista ratkaisua tähän ei löytynyt.
