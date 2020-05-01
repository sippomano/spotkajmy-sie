# spotkajmy-sie
Simple meeting planner that compares two calendars and finds free spaces that can fit a meeting of passed duration

W zadaniu wydaje się być błąd, lub nie do końca je zrozumiałem.
Jeden z zakresów jest niemożliwy do osiągnięcia przy przykładowych kalendarzach. 
Spotkanie z przykładowego outputu nie może się odbyć w godzinach 18:00-18:30, ponieważ w kalendarzu 1 odbywa się w tych godzinach spotkanie.
W mojej wersji programu w outpucie podane są zakresy w których spotkanie może się rozpocząć przy założonej jego długości.
Uznałem to za bardziej czytelne, inaczej użytkownik musiałby odjąć długość spotkania od zakresu samodzielnie, aby dowiedzieć się o której najpóźniej może się rozpocząć.
W tem wypadku program podaje zakres w któryma można zorganizować-rozpocząć spotkanie, nie zakresy w których może się odbyć.
Program akceptuje godziny w formacie XX:YY, jak i samą godzinę w postaci pojedynczej cyfry.
Program zamienia godziny w minuty  i na nich wykonuje wszystkie operacje.
Dodałem komentarze opisujące działanie poszczególnych fragmentów kodu po angielsku, sugerując się faktem,
iż kalendarze podane w zadaniu były w języku angielskim. Gdyby było to problemem, proszę o informację, przetłumaczę je na język polski.