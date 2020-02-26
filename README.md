# Wild Wild West

Tested on API 29 with the following devices
* Pixel 3a XL
* Nexus 5
* Nexus 7

**Target Audience**: English speaking Person of Age 8 to 70 who has Android Device and who know about Western Gun Showdown Culture.

# Technical components involved:
* **Multimedia:** Images used as backgrounds, logo and decorators. Audio of “READY”,”FIRE”, gunshot sound played when Practicing or Dueling. Music played on loop in the Home-Screen (MainActivity.java).
* **Sensor**: Accelerometer is the core mechanic of the game – it is used to understand the position of the phone in space.
* **Local Storage**: SQLite is used to ensure that data remains locally in the phone. It stores the following information:
  * Name (Displayed in the Home-Screen and Duel)
  * Email and Password (Used to authenticate user)
  * High-score (Displayed in Home-Screen as null when you first register and is updated when it is lower than your previous time or high-score is null)
* Server Support: The Chatroom uses one Phone as the local server and others as Clients to communicate between each other with the condition being that the phones are in the same network.
