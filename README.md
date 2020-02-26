# Wild Wild West
Howdy Partner! Wild Wild West is an android mobile game which aims to emulate the old west gun showdown. You can easily sign up and get more personalized experience without any worry of privacy as the data is stored locally in the device – a big relief for the parents! 

The aim is to create a fun experience between friends/family by spending time together. You can either practice your gunslinging skills in the practice room or go head to head in the duel against family or friends by using the same phone.

### Gameplay
Keep your phone in the rest position like shown in the practice room and then press “START”. When you hear “READY”, wait to hear “FIRE” (or else you get reset!) to raise your phone in the attack position to outdraw your poor opponent who did not even hear the gunshot coming. 

When all is done, show off your high scores to your allies and your enemies. The town is never empty – connect phones in the same 
network to share stories of great adventures to your friends and foes in the chatroom.


## Tested on
API 29 with the following devices
* Pixel 3a XL
* Nexus 5
* Nexus 7

**Target Audience**: English speaking Person of Age 8 to 70 who has Android Device and who know about Western Gun Showdown Culture.

# Technical components:
* **Multimedia:** Images used as backgrounds, logo and decorators. Audio of “READY”,”FIRE”, gunshot sound played when Practicing or Dueling. Music played on loop in the Home-Screen (MainActivity.java).

* **Sensor**: Accelerometer is the core mechanic of the game – it is used to understand the position of the phone in space.

* **Local Storage**: SQLite is used to ensure that data remains locally in the phone. It stores the following information:
  * Name (Displayed in the Home-Screen and Duel)
  * Email and Password (Used to authenticate user)
  * High-score (Displayed in Home-Screen as null when you first register and is updated when it is lower than your previous time or high-score is null)
  
* **Server Support**: The Chatroom uses one Phone as the local server and others as Clients to communicate between each other with the condition being that the phones are in the same network.
