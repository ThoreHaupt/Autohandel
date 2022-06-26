# Autohandel
ProKSyAbgabe Thore Haupt

Github Repo Link: https://github.com/ThoreHaupt/Autohandel.git


# Bekannte nicht erfüllte Voraussetzungen:
- Man kann nicht alle Auswahlbereiche gleichzeitig sehen

Noch wichtig:

Dieses Programm hat eine eigene Datenbank mit ca 200 oder mehr eigenen Autos...
Diese haben alle Bilder, die aber aus dem Netz heruntergeladen werden müssen.
Ich habe kein system das dynamisch zu machen, wenn die Einträge angezeigt werden. Hätte ich mir mehr Zeit aber egal.
Die Konsequenz ist, dass das starten des Programms ca 20-30 Sekungen dauert(je nach Internet). Deswegen kann man das Abschalten, indem man
im Controller die Variable "downloadImages" auf false setzt.

# Anmerkungen:

Ich habe ein wenig Code aus dem Internet stibitzt: 
Der Range Slider ist von dem folgenden Link/ Tutorial, den habe ich nicht komplett selber gemacht, viel zu hoch und auch
deutlich außerhalb der Anforderungen würde ich einfach mal sagen: (der RangeSlider hat auch keine in der AufgabenStellung geforderte Funktionalitäten)
https://ernienotes.wordpress.com/2010/12/27/creating-a-java-swing-range-slider/
GitHub: https://github.com/ernieyu/Swing-range-slider
Jedoch habe ich eine Reihe an Änderungen gemacht, um das an mein LaF anzupassen/ die Form vom Silder zu verändern usw...

Hier der Link zu dem Open Source LaF: https://www.formdev.com/flatlaf/
Dieses LaF läuft unter einer Apache 2.0 License. Link: https://github.com/JFormDesigner/FlatLaf/blob/master/LICENSE

Ich hatte mit Phillip gesprochen, der meinte das es ok sei externe ressources benutzen, soweit dies nicht die Compilierbarekeit des Codes nach
dem Import zerstören
Das sollte nicht der Fall sein, da die Jar dem Class Path hinzugefügt ist.
Jedoch kann man das sonst auch entfernen, indem man sowhol in der Klasse LoadingProgressbar und im UIController die import und setUp() statementes auskommentiert.

Kontakt:
thore.haupt@student.kit.edu
0173 1691314
