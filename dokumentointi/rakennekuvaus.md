## Rakennekuvaus

**Kehysympäristö**: *framework* paketit
- *mipuz*: Main funktio joka käynnistää ohjelman
- *mipuz.game*: Sisältää kehysympäristön tarjoaman rajapinnan ja siihen liittyvät oheisluokat. Pelit käyttävät näitä saadakseen tietoa kehysympäristöltä ja kertovat kehysympäristölle kun niiden toiminta päättyi.
- *mipuz.logic*: Sisältää pelien hallinnan ja käynnistämisen. Uuden pelin tuominen kehysympäristöön vaatii `Enginge.java` luokan metodiin `loadGames` pelin `Game` rajapinnan toteuttavan luokan parametrittoman konstruktorin kutsun lisäämisen. Mitään muuta muutosta ei tarvita.
- *mipuz.ui*: Vastaa kehysympäristön käyttöliittymästä ja siihen kuuluvista toiminnoista.
- *utilities*: Sisältää apukirjastorutiineja joita sekä kehysympäristö että pelit voivat hyödyntää.

**Peli(t)**:
Tällä hetkellä ne noudattavat seuraavanlaista jakoa (esimerkkinä BBGame):
- *BBGame*: Pelinniminen paketti sisältää ns. kuoren joka toteuttaa Game rajapinnan ja toimii pelin eri osien kokoajana.
- *.event*: Sisältää pelin sisäiset tapahtumat ja niihin liittyvän toiminnallisuuden esim. omat tapahtumaluokat ja rajapinnat.
- *.logic*: Sisältää pelin varsinaisen toiminnallisuuden eli toteuttaa ns. pelin moottorin.
- *.ui*: Sisältää peli käyttöliittymän eri osiot ja niihin liittyvän toiminnallisuuden.

Pelien ns. moottori, edellä kerrotty logic paketti, toteuttaa pelin siten että pelin käyttöliittymän, ui paketti, voi vaihtaa ilman että pelin toimintaan, siis logic pakettiin, tarvitsee tehdä muutoksia. Näin käyttöliittymän voi toteuttaa eri tavoin ja sen voi vaihtaa toisen paikalle ilman että pelin ydintä tarvitsee muuttaa. Toki kuoreen, esim. BBGame, joutuu tekemään tarpeelliset muutokset. Jos tutkit kuinka BBEngine tai MMEngine on toteutettu huomaat miten käyttöliittymä tekee pelistä melko erilaisen kuin niiden koodin perusteella voisi kuvitella. BBGame on lähempänä mitä pelimoottoria tutkimalla voisi ajatella kun taas MMGame on erilaisempi.


