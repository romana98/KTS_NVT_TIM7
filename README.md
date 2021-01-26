# KTS_NVT_TIM7
Vukašin Ocokoljić sw60/2017,
Romana Erdelji sw58/2017,
Milorad Radović sw67/2017,
Vera Kovačević sw19/2017


Za wireframe smo koristili Balsamiq.

# Pokretanje projekta

1. Pokretanje Sistema za upravljenje bazom podataka - PgAdmin4
2. Pokretanje aplikativnog servera

## 1. Konfiguracija PgAdmin

- Ukoliko je instaliran PgAdmin, pozicionirati se u <tt>PostgreSQL\12\data</tt> i podesiti METHOD u trust svuda, ili u application.properties staviti svoje kredencijale za bazu.

## 2. Pokretanje aplikativnog servera

Pokretanje aplikativnog servera podrazumeva kompajliranje Java koda i njegovo pokretanje u JVM izvršnom okruženju.

- Preuzeti maven
- Pozicionirati se na root ovog projekta i izvršiti maven naredbu: 
> mvn clean compile install

Dva glavna koraka ove naredbe su kompajliranje frontend i backend koda, a međukoraci su preuzimanje modula od kojih zavise, kopiranje izgrađenog frontend koda (html/js/css) u direktorijum odakle se servira statički sadržaj bekend servera; potom kompajliranje Java koda bekenda i izgradnja .jar arhive. Svi ovi koraci su dirigovani sadržajem pom.xml fajlova u direktorijumima root, frontend i klinika.

- Pozicionirati se u <tt>t05-ng-app</tt> i pokrenuti:
> npm install, a nakon toga ng serve

- Adresa na beku:  http://localhost:8080
- Adresa na frontu: http://localhost:4200
