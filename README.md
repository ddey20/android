Proiect DAM
===========

Android Studio - Quiz App

FAZA 3
======

### 1. Utilizarea fișierelor de preferințe; (0.5 p.)
	- SharedPreferences folosit in clasa Session apelata dupa aceea in multe alte activitati, si separat in activitatea Settings    
### 2. Crearea unei baze de date SQLite cu minim două tabele cu legături intre ele (o tabelă/membru). Implementarea operațiilor de tip DDL; (0.5 p.)
    - Baza de date a fost creata inca din prima faza, iar in aceasta faza am asigurat implementarea tuturor operatiilor CRUD pentru fiecare dintre cele 5 tabele SQLite.
###  3. Implementarea operațiilor de tip DML. Pentru fiecare tabela sa se implementeze cel puțin o metoda de insert, update, delete si select. Toate metodele trebuie apelate; (0.5 p.)
	- DbHelper contine tot ceea ce este legat de baza de date ( toate operatiile CRUD pentru fiecare tabela -> minim 25 de operatii care sunt apelate).
	- Alte detalii:
		- update pe quizz, question, answer in Inactive Quizzes (quizul trebuie sa fie inactiv pt a putea fi modificat)
		- delete pe results in StudentsResults
		- delete pe user in EditProfile
###  4. Definirea a minim două rapoarte pe datele stocate în baza de date. Prin raport, se înțelege afișarea pe ecranul dispozitivului mobil a informațiilor preluate din baza de date. Rapoartele sunt diferite ca si structura. (0.5 p.). Componentele vizuale în care se afișează cele doua rapoarte trebuie sa fie
diferite de cele prezentate la faza 1 și 2.
	- Am modificat cateva componente vizuale si am definit cele 2 rapoarte pe datele stocate in baza de date (in 2 ListViewuri cu adaptoare diferite) implementate in StudentsResultsActivity si QuizHistoryActivity de unde vom exporta ulterior fisierele CSV si TXT.
###  5. Salvarea rapoartelor în fișiere normale. (txt, csv etc.) (0.5 p.)
	- Export CSV regasit in StudentsResultsActivity ( pentru Teacher )
	- Export TXT regasit in QuizHistoryActivity ( pentru Student )

##### Am facut modificari in urma primirii feedbackului de la faza 1.
