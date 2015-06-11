BBGame
======
**Goal**: Set bit value buttons to equal to target value shown on top of the screen.

**How**: Once you have selected game level you will be presented game display. On top of the screen is Target: *xxx* where *xxx* is value you try to set using provided buttons. Each button presents one bit from MSB, most significant bit, located on the left/top on the row(s) to LSB, least significant bit, located on the right/bottom on the row(s). Once you have reached correct value the game is over.
 
For example if target value is `13` you will need to set bits like this `1101`. The first, or leftmost, bit on previous example corresponds to decimal value 8. The second bit is 4, the third bit is 2 and the fourth bit is 1. Thus example spells 8 + 4 + 0 + 1 = 13.

On easy level values are between *0...255*.

On medium level values are between *0...65535*.

On hard level values are between *0...4294967295*.

By default there is no visible value as what currently selected bits yield but in case you need help with this you can obtain visible value by pressing `Help` button on the bottom of the screen.
