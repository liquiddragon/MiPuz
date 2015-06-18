Kehysympäristö
==============
**Käyttö**: Käynnistä sovellus, valitse listalta haluamasi peli ja paina Play game nappulaa.

**Usage**: Startup the application, select desired game from the list and press Play game button.

BBGame
======
**Tavoite**: Aseta bittien arvot nappuloilla siten että ne muodostavat ylälaidassa näkyvän luvun.

**Toiminta**: Valittuasi pelin vaikeustason näet pelin käyttöliittymän. Ruudun ylälaidassa on Target: *xxx* joka esittää kokonaislukua joka sinun täytyy saada aikaiseksi annetuilla arvoilla. Jokainen nappula esittää yhtä bittiä siten että merkitsevin bitti on vasemmalla/ylhäällä ja vähiten merkitsevä bitti on oikealla/alhaalla. Kun olet muodostanut oikean binääriluvun peli päättyy.

Esimerkiksi jos luku on `13` sinun täytyy asettaa bitit, eli painikkeet, siten että ne muodostavat jonon `1101`. Ensimmäinen, eli vasemmanpuoleisin, bitti edellä vastaa kymmenkantaisen luvun arvoa 8. Toinen bitti arvoa 4, kolmas arvoa 2 ja neljäs arvoa 1. Esimerkki on siis 8 + 4 + 0 + 1 = 13.

Helpolla tasolla luvut ovat väliltä *0...255*.

Keskimmäisellä tasolla luvut ovat väliltä *0...65535*.

Vaikeimmalla tasolla luvut ovat väliltä *0...4294967295*.

Oletuksena näytöllä ei näy lukua minkä asettamasi bitit muodostavat, mutta jos tarvitset apua tämän kanssa voit painaa `Help` nappulaa ruudun alareunassa.

Vinkki: Ellei binäärijärjestelemä ole tuttu käy kurkistamassa selitys [Wikipediasta](https://fi.wikipedia.org/wiki/Bin%C3%A4%C3%A4rij%C3%A4rjestelm%C3%A4).

--

**Goal**: Set bit value buttons to equal to target value shown on top of the screen.

**How**: Once you have selected game level you will be presented game display. On top of the screen is Target: *xxx* where *xxx* is value you try to set using provided buttons. Each button presents one bit from MSB, most significant bit, located on the left/top on the row(s) to LSB, least significant bit, located on the right/bottom on the row(s). Once you have reached correct value the game is over.
 
For example if target value is `13` you will need to set bits like this `1101`. The first, or leftmost, bit on previous example corresponds to decimal value 8. The second bit is 4, the third bit is 2 and the fourth bit is 1. Thus example spells 8 + 4 + 0 + 1 = 13.

On easy level values are between *0...255*.

On medium level values are between *0...65535*.

On hard level values are between *0...4294967295*.

By default there is no visible value as what currently selected bits yield but in case you need help with this you can obtain visible value by pressing `Help` button on the bottom of the screen.

Hint: If binary numbers are unfamiliar to you check them out from [Wikipedia](https://en.wikipedia.org/wiki/Binary_number).


MMGame
======
**Tavoite**: Paina nappuloita järjestyksessä jonka ohjelma esittää.

**Toiminta**: Valittuasi peli vaikeustason näet pelin käyttöliittymän. Varsinainen peli alkaa vasta painettuasi `Go` nappulaa joten sinulla on aikaa tutustua pelin käyttöliittymään. Kun peli alkaa se valitsee jonkin esillä olevista nappuloista ja vaihtaa sen esitystä hieman, esimerkiksi muuttamalla nappulan kuvan harmaaksi tai vaihtamalla sen kuvan toiseksi. Ole tarkka mitä nappuloita peli valitsee sillä sinun pitää valita ne samassa järjestyksessä. Aluksi peli valitsee vain yhden nappulan jonka jälkeen on sinun vuorosi. Onnistuttuasi tässä peli lisää sarjaan toisen nappulan eli se valitsee juuri äsken valitun nappulan ja jonkin toisen nappulan. Tämän jälkeen on taas sinun vuorosi valita nappulat samassa järjestyksessä. Peli päättyy kun olet onnistuneesti saanut suoritetuksi valitun sarjan.

Helpolla tasolla sarjan pituus on 6.

Keskimmäisellä tasolla sarjan pituus on 8.

Vaikeimmalla tasolla sarjan pituus on 10.

*Vinkki*: Jos vahingossa valitset väärän nappulan jatka painamalla mitä tahansa nappuloita kunnes olet painanut niin montaa nappulaa kuin sarjaan kuuluu. Tämän jälkeen peli näyttää alkuperäisen sarjan uudestaan. Muista että pelissä ei ole aikarajoja tai pisteitä joita voit menettää.

--

**Goal**: Press buttons in given order to form indicated sequence.

**How**: Upon selecting game level you will be presented game interface. Actual game begins once you press `Go` button in order to give you time to familirialise yourself with interface. Once the game begins it will select one of the buttons presented and causes it to alter its presentation slightly, e.g. to gray scale or another image. This selection is also called blink, i.e. the button blinks. Pay close attention to buttons. First time around only one button will be blinked and you will need to press that same button. After that the first button is blinked followed by the second button. After that it is your turn to press them in same order. Next third button is added to sequence and so on. The game ends once you have successfully performed presented sequence.

On easy level sequence length is 6.

On medium level sequence length is 8.

On hard level sequence length is 10.

*Hint*: If you press incorrect button continue on pressing any buttons until you have pressed that many buttons that current sequence requires and the game will show the original sequence again. Remember there is no time limit or points to loose.

