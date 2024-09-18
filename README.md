# Precizari

In scrierea temei, am folosit ca baza functile de citire si de scriere de la laborator pe care
le-am modificat pentru a respecta cerintele temei.

## Problema 1 - Servere
### Descrierea rezolvarii
Pentru a rezolva aceasta problema, am folosit o metoda Divide et Impera.

La inceput, am definit 2 functii pentru a calcula `minimul` si `maximul` pe sirul de valori ale
curentului. Aceste 2 valori vor deveni `capetele intervalului de cautare`. Am definit si functia
`minPower`, o functie care pentru un prag de curent dat, calculeaza puterea minima de calcul.

Apoi, am calculat continuu `mijlocul` si cautam cautarea pe intervalul corespunzator cu puterea 
minima mai mare, pana cand intervalul a devenit mai mic decat o valoare de `toleranta`, definita de
mine ca 0.001.
### Complexitate
Consideram `start` si `end` valorile initiale ale capetelor intervalului.

Avand in vedere ca impartim intervalul in doua pana la o anumita toleranta, avem un o complexitate
temporala de O(log(start - end)). La fiecare iteratie apelam minPower cu complexitate liniara, deci
se ajunge la O(n * log(start - end))
> Complexitate temporala: O(n * log(start - end))

Folosim numai variabile simple, deci avem complexitate spatiala O(1), daca luam in considerare
inputurile(2 vectori), atunci avem O(n).
> Complexitate spatiala:
> - O(1), fara date de intrare
> - O(n), cu date de intrare

## Problema 2 - Colorare
### Descrierea rezolvarii
Am folosit o abordare matematica pentru a rezolva problema, voi incepe prin a descrie interactiunile
intre colorarile portiunilor verticale si orizontale

**Vertical la inceput:**
O piesa verticala la inceput are `3` posibilitati de colorare.

**Orizontal la inceput:**
Cand se incepe cu 2 piese orizontale suprapuse, exista A<sup>2</sup><sub>3</sub>, adica `6`
posibilitati de colorare.

**Vertical urmat de vertical:**
Intrucat o culoare este ocupata, urmatoarea portiune are `2` posibilitati de colorare.

**Orizontal urmat de orizontal:**
Urmatoarele 2 segmente suprapuse pot avea culorile celui dinainte, doar ca inversate, sau una dintre
ele inlocuita de a 3a culoare, avand pertotal `3` posibilitati de colorare.

**Vertical urmat de orizontal:**
O culoare este luata de zona verticala, deci cele 2 segemente orizontale pot avea doar 2 culori, pe
care le pot interschimba, avand astfel `2` colorari.

**Orizontal urmat de vertical:**
Cele 2 segmente suprapuse ocupa 2 culori, deci mai exista `1` colorare.

Am folosit formula din lab: `(a * b) % MOD = ((a % MOD) * (b % MOD)) % MOD`, la calcularea
produselor. Iar cand modulul intorcea 0, il resetam la 1.

Deoarece pot exista mai multe potiuni de acelasi fel una langa alta, am implementat o functie de
putere eficienta care se foloseste de modul.

### Complexitate
Pentru simplitatea calculelor, presupunem ca toate campurile din vectorul num(cu numerele de
portiuni) au valoarea m.

Functia de putere este bazata pe recurenta: x<sup>2n</sup> = x<sup>n</sup> * x<sup>n</sup>,
x<sup>2n+1</sup> = x * x<sup>n</sup> * x<sup>n</sup>, x<sup>0</sup> = 1 transformata iterativ pentru
eficienta, deci are complexitatea O(log(exp)).

Avand n numere in sir, care sunt exponenti cu valoarea m, avem complexiatea temporala O(n,m) = 
O(n * log(m))
> Complexitate temporala: O(n * log(m)) 

Folosim variabile simple, deci avem complexitate spatiala O(1) fara inputuri si O(n) cu inputuri.
> Complexitate spatiala:
> - O(1), fara date de intrare
> - O(n), cu date de intrare

## Problema 3 - Compresie
### Descrierea rezolvarii
Am creat vectorii dp1 si dp2, 2 vectori care tin pentru a si b in dp1[i] si dp2[i] suma tuturor
elementelor pana la elementul de pe pozitia i.

Primul element din compresia comuna apare in ambii vectori. Daca ar fi ca din dp1 si dp2 sa scadem
aceasta valoare, urmatoarea valoare egala a acestora va fi al doilea element al compresiei.

Putem, astfel, sa ajungem la concluzia ca lungimea secventei maxime de compresie este numarul de
elemente comune ale lui dp1 si dp2.

Numerele dp1[n] si dp2[m] sunt de asemenea sumele sirurilor, dupa care putem verifica existenta
compresiei comune.

Pentru a gasi eficient numarul de elemente comune, am adaugat elementele lui dp1 intr-un hashmap si
am verificat pentru fiecare element al lui dp2 daca exista in hashmap.

### Complexitate
Calcularea vectorilor de sume are ca timp `O(n)`, respectiv `O(m)`.

Deoarece adaugarea in hashtable se face in O(1), adaugarea elementelor lui dp1 se face in `O(n)`

Deoarece accesarea elementelor din hashtable se face in O(1), cautarea elementelor lui dp2 se face
in `O(m)`

> Complexitate temporala: O(n + m)

Folosim vectorii dp1 si dp2, deci complexitatea spatiala este de O(n+m)

> Complexitate spatiala: O(n + m)

## Problema 4 - Criptat
### Descrierea rezolvarii
Am definit clasa `Word` care tine un cuvant, lungimea lui si numarul de aparitii al fiecarei litere
din alfabet in cuvant. Pe langa ea, am definit un `comparator`, care compara literele dupa de cate ori
apare o litera primita la constructie sau dupa lungime in caz in care sunt egale.

Dupa ce am transformat inputurile in clase Word, parcurgem alfabetul, si, pentru fiecare litera,
sorta lista de cuvinte dupa frecventa ei, urmand sa adaugam cuvintele in parola. Facem maximul pe
lungimea parolelor.

Literele care nu apar predominant in cel putin un cuvant sunt ignorate.

### Complexitate
Numarul literelor din alfabet este constant. Deci calcularea lungimii parolei se face in O(n).
Presupunem lungimea maxima a cuvintelor ca fiind m, constructia listei de Word dureaza astfel O(m*n).
La fiecare litera a alfabetului, se realizeaza si o sortare(cu complexitate n * log(n)), ducand
complexitatea temporala la O(n * log(n))

> Complexitate temporala: O(n * m + n * log(n))

Intrucat memeoram listele de frecventa, avem o complecitate spatiala de O(n).

> Complexitate spatiala: O(n)

## Problema 5 - Oferta
### Descrierea rezolvarii
Am inceput prin a calcula vectorul cu preturile produselor grupate cate 2, respectiv 3.

Apoi rezolvarea noastra se imparte pe 2 cazuri.

Cand k este 1, parcurgem liniar avand un vector dp cu pretul minim la cumpararea a i obiecte.
dp[0] = 0 este cazul de baza(pretul este 0 pt 0 obiecte), iar dp[i] = min(dp[i-1] + price[i],
dp[i-2] + two[i], dp[i-3] + three[i]). La final, in dp[n] se va afla solutia.

Cand k este 2, generam recursim toate sumele posibile si le adaugam intr-un heap, urmand sa scoatem
din heap primele k solutii, pentru a ajunge la cea dorite.

### Complexitate
Pe cazul k = 1, constructia lui two, three si aplicarea partii de programare se realizeaza in timp
liniar, avand astfel complexitate O(n).

> Complexitate temporala: O(n)

Construim vectorii two, three si dp, deci avem complexitate spatiala O(n)

> Complexitate spatiala: O(n)

Varianta k > 1 presupune calcularea si memorarea tuturor sumelor, avand astfel o complexitate
temporala si spatiala exponentiala.