Prihlasovacie udaje(case sensitive !):
meno - marek
heslo - 123
Zvysne prihlasovacie udaje mozu byt najdene v subore credentials.txt, resp. vytvorene registraciou noveho uzivatela v grafickom prostredi aplikacie

PS: na testovacie ucely odporucam vybrat/oznacit minimalne prve 3 suroviny (Apples, Bread, Cheese) - tieto suroviny su dostatocne na recepty s nazvom TEST a TEST3


KRITERIA:
1) dedenie - baseController a LoginController - showMessage, setMessageTextColor, v buducnosti dalsie...
2) polymorfizmus - umožňuje receptom mať odlišné vlastnosti - bezlepkový alebo vegetariánsky "badge". Keď aplikácia zobrazuje recepty, môže dynamicky prispôsobiť obsah a grafiku bez ohľadu na ich špecifický typ. Neskor sa na zaklade tohoto rozdelenia bude zobrazovat samotna ikonka / badge. Zatial je vypisana len pravdivostna hodnota.
3) agregacia - User má Inventory. User obsahuje Inventory, ale Inventory môže existovať nezávisle od User.
4) oddelenie aplikacnej logiky a pouzivatelskeho rozhrania - ANO - fxml, controllery, zvysok...

Co uz funguje:
Prihlasovanie / registracia (mali by byt pokryte vsetky edge-case, napr. pouzivatel sa pokusa registrovat uz existujuceho uzivatela.. V tychto pripadoch sa v GUI aplikacie zobrazi prislusna sprava)
Vyberanie surovin, ktore ma pouzivatel doma.
Vyhladavanie v surovinach
Prepojenia medzi obrazovkami(vracanie sa spat pomocou tlacitok BACK)
Vyberanie receptov, na ktore ma pouzivatel suroviny
Zobrazenie konkretneho receptu


Co treba este zlepsit / dokoncit:
pridat moznost odhlasit sa a prihlasit sa ako iny pouzivatel
pridat realne recepty a zosuladit ich s podporovanymi surovinami
Upravit zobrazenie kontretnych receptov
    - pridat moznost hodnotit (like/dislike) dane recepty
