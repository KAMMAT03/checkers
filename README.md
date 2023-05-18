# APRO2 2023L - Checkers 


## Autorzy:
- Zespół 1. : Jędrzej Stefańczyk, Anastasiya Kuhach, Klemens Zalewski, Urszula Kępka
- Zespół 2. : Bartłomiej Dmitruk, Emilia Jasińska, Wanessa Kurowska, Kamil Matuszewski, Maciej Matuszewski

### Dokumentacja: 
https://www.overleaf.com/2593458417pscsqgdpqzbg

### Diagram UML:
https://drive.google.com/file/d/1yhbuji-iQKcc6W3OwzqRUG-49MCI4BR9/view?usp=sharing

### Issues (Potrzebne poprawki po etapie pierwszym):
- Brak utworzenia szkieletu klas i metod - konieczność wykonania tego zadania przez grupę realizującą etap 2.
- Brak klasy "Board" odpowiedzialnej za planszę. Istnienie "Board" w specyfikacji projektu okrojone jest do postaci tablicy. Konsekwencją tego jest niefunkcjonalność systemu. 
- Brak opisu mechanizmu tworzenia damek - nieprzewidzenie metod i pól w klasie Field odpowiadającym temu zagadnieniu
- Źle wypisany sposób przekazywania Game w metodach do połączenia serwerowego
- Zły sposób wykonywania ruchów - klient nie powinien mieć dostępu do metody makeMove tylko wysyłać dane do serwera aby ten się tym zajmował ze względu na bezpieczeństwo
- brak metody reciveMove() w połączeniu serwerowym - uwzględniono tylko istniene metod sendMove()
- zbędna metoda ChangeBoard i CheckGameLogic - działania które ma wykjonywać są juz wykonywanie w kodzie gry, jako ze sprawdzanie logiki gry musi równiez dzialać pezy grze lokalnej, nie tylko serwerowej


