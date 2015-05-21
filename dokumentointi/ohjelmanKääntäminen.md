# Ohjelma vaatii Java version 8
Tällä hetkellä Ubuntun versioon 14.04 LTS ei ole saatavana OpenJDK versiota 8
suoraan Ubuntun normaaleista repoista johtuen kyseisessä Ubuntun versiossa olevista
ongelmista ja riippuvuuksista. Kts.
[Needs packaging openjdk-8 in 14.04](https://bugs.launchpad.net/ubuntu/+source/openjdk-8/+bug/1341628).

[Matthias Klose](https://launchpad.net/~doko) on tehnyt PPA:n mihin hän on paketoinut
OpenJDK 8 Ubuntun käyttöön.

**Toimi seuraavasti**:

1. Avaa terminaali ikkuna joko valikosta tai painamalla Ctrl-Alt-T.
2. Lisää PPA paketinhallintajärjestelmän käytöön seuraavalla komennolla:
 ```
 sudo add-apt-repository ppa:openjdk-r/ppa
 ```
3. Päivitä paketinhallintajärjestelmän välimuisti:
 ```
 sudo apt-get update
 ```
4. Asenna OpenJDK 8
 ```
 sudo apt-get install openjdk-8-jdk
 ```
5. Jos käytössäsi on useita Javan versioita aseta haluamasi seuraavilla komennoilla:
 ```
 sudo update-alternatives --config java
 ```
ja oletus Java kääntäjä:
 ```
 sudo update-alternatives --config javac
 ```
6. Tarkista että muutokset tulivat voimaan
 ```
 java -version
 ```

Tuloksen pitäisi olla tämän tyylinen:
```
openjdk version "1.8.0_45-internal"
OpenJDK Runtime Environment (build 1.8.0_45-internal-b14)
OpenJDK 64-Bit Server VM (build 25.45-b02, mixed mode)
```

Alkuperäinen englanninkielinen ohje löytyy [täältä](http://ubuntuhandbook.org/index.php/2015/01/install-openjdk-8-ubuntu-14-04-12-04-lts/).

**Kehitysympäristön asetusten tarkistaminen**
Mikäli käytät graafista kehitysympäristöä muista tarkistaa sen asetuksista että
sekin käyttää oikeaa versiota kääntäjästä. Esimerkiksi NetBeans:

1. Valitse projektin konteksivalikko ja sieltä Properties
2. Valitse Compile Build otsikon alta
3. Varmista että Java Platform on JDK 1.8
* Ellei näin ole lisää se itse painamalla Manage Java Platforms nappulaa
  * Esiin tulevasta ikkunasta paina Add Platform ... nappulaa
  * Valitse Java Standard Edition ja paina Next nappulaa
  * Valitse oikea hakemisto joka oletuksena on /usr/lib/jvm hakemiston alla, esim. java-1.8.0-openjdk-amd64 ja paina Next nappulaa
  * Anna alustan nimi kohtaan Platform name, esim. JDK 1.8 ja paina Finish nappulaa
  * Valitse nyt juuri luomasi JDK 1.8 Java Platform pudotusvalikosta

