# Cartographie du contenu pédagogique — Liteschreib IKII

## 1. Objectif du document

Ce document relie chaque unité de contenu de l'application à :
- un niveau scolaire du système éducatif francophone camerounais (6ème à Terminale),
- un niveau GeR/CECR,
- une unité/chapitre du curriculum *Ihr und Wir Plus* (référence à confirmer par toi sur le manuel réel),
- une œuvre ou un extrait littéraire support,
- des objectifs d'apprentissage et un type d'exercice.

Il doit être rempli et validé (par toi et/ou ton encadrant académique) **avant** l'implémentation de la Mission A4 (entités Room et pré-population) — cette validation constitue la **Mission A0** du backlog (`04-missions-et-sprints.md`).

> ⚠️ **Ce que ce document affirme avec certitude vs. ce qui reste à valider par toi** : la correspondance niveau scolaire ↔ niveau GeR (section 2) est **une proposition raisonnée**, construite sur des principes généraux de progression CECR en langue vivante — je n'ai pas accès au texte du curriculum officiel camerounais ni au manuel *Ihr und Wir Plus*, donc les références de chapitres restent des emplacements à compléter par toi. Les unités d'exemple (section 3) sont des **textes originaux que j'ai rédigés moi-même**, calibrés au niveau visé, à faire relire par un locuteur natif ou ton encadrant avant toute utilisation réelle (cohérent avec ADR-006 — approche hybride domaine public + texte original).

## 2. Population cible et correspondance niveau scolaire ↔ niveau GeR

La population cible finale couvre l'intégralité du secondaire francophone camerounais, de la **6ème à la Terminale** (7 années). **Décision de portée (tranchée)** : le MVP se concentre sur le **premier cycle complet (collège : 6e, 5e, 4e, 3e)** ; le second cycle (lycée : 2nde, 1ère, Terminale) est reporté à un développement ultérieur (probablement Cycle DBR 2).

### Correspondance niveau scolaire ↔ niveau GeR (proposition à valider)

| Niveau scolaire | Cycle | Année d'apprentissage de l'allemand (hypothèse : continu depuis la 6e) | Niveau GeR visé (proposition) | Unités cibles indicatives | Phase de développement |
|---|---|---|---|---|---|
| 6ème | Collège (1er cycle) | 1ère année | A1 (découverte) | 6 à 8 | **MVP actuel** |
| 5ème | Collège (1er cycle) | 2e année | A1 (consolidation) → A1/A2 | 6 à 8 | **MVP actuel** |
| 4ème | Collège (1er cycle) | 3e année | A2 (découverte) | 6 à 8 | **MVP actuel** |
| 3ème | Collège (1er cycle) — BEPC | 4e année | A2 (consolidation) | 6 à 8 | **MVP actuel** |
| 2nde | Lycée (2nd cycle) | 5e année | B1 (découverte) | 5 à 7 | Développement ultérieur |
| 1ère | Lycée (2nd cycle) | 6e année | B1 (consolidation) | 5 à 7 | Développement ultérieur |
| Terminale | Lycée (2nd cycle) — Baccalauréat | 7e année | B1+ / B2 (selon filière et intensité) | 5 à 7 | Développement ultérieur |

**Avantage de ce resserrement** : le MVP couvre une bande CECR homogène (A1 à A2), ce qui réduit la variance de niveau par rapport à une couverture étalée jusqu'à B2 — cohérent avec une réduction (partielle) du risque R-07, même si le volume total (20 unités visées) reste conséquent.

**Hypothèses à confirmer avec toi** :
- Que l'allemand est bien étudié en continu de la 6e à la 3e par la population cible du MVP.
- Que la progression GeR ci-dessus correspond à celle réellement visée par *Ihr und Wir Plus* pour le collège.

### Repères thématiques indicatifs par niveau GeR (pratique standard DaF, à adapter)

| Niveau GeR | Thèmes typiques | Type de support littéraire adapté |
|---|---|---|
| A1 (6e, 5e) | Se présenter, la famille, l'école, les nombres/couleurs, la maison, les animaux, les routines simples, les loisirs | Contes très simplifiés/adaptés, comptines, phrases courtes originales |
| A2 (4e, 3e) | Le quotidien, les loisirs, les achats, la nourriture, les fêtes, un voyage/des vacances, récit au Perfekt, projets simples | Contes complets simplifiés (ex. inspirés des frères Grimm, domaine public), courtes nouvelles adaptées |
| B1 (2nde, 1ère) *(ultérieur)* | Exprimer une opinion, raconter une expérience passée, parler de projets, environnement, comparaison culturelle | Extraits de nouvelles courtes, poèmes classiques accessibles (Goethe, Heine — domaine public) |
| B1+/B2 (Terminale) *(ultérieur)* | Sujets abstraits, argumentation, société, tradition et modernité | Extraits authentiques de littérature classique (Kafka, Goethe, Heine — domaine public), textes réflexifs originaux |

## 3. Matrice de correspondance et unités de contenu

### 3.1 Unités du MVP actuel (collège : 6e, 5e, 4e, 3e)

| ID Unité | Niveau scolaire | Niveau GeR | Chapitre *Ihr und Wir Plus* | Œuvre/extrait littéraire support | Objectifs d'apprentissage (écrit) | Type d'exercices | Statut du contenu | Statut des droits |
|---|---|---|---|---|---|---|---|---|
| U-6E-01 | 6ème | A1 | *(à compléter)* | Texte original — « Ich stelle mich vor » (voir 3.2) | Se présenter à l'écrit, vocabulaire de base | QCM, écriture guidée | Rédigé (brouillon) | Texte original |
| U-5E-01 | 5ème | A1 (consolidation) | *(à compléter)* | Texte original — « Meine Woche » (voir 3.2) | Décrire sa semaine, expressions de temps, subordonnée « weil » simple | QCM, vrai/faux, écriture guidée | Rédigé (brouillon) | Texte original |
| U-4E-01 | 4ème | A2 | *(à compléter)* | Texte original — « Ein Tag in Yaoundé » (voir 3.2) | Raconter sa journée, Perfekt de base | QCM, texte à trous, écriture guidée | Rédigé (brouillon) | Texte original |
| U-3E-01 | 3ème | A2 (consolidation) | *(à compléter)* | Texte original — « Meine Sommerferien in Kribi » (voir 3.2) | Raconter des vacances au passé (Perfekt étendu), exprimer un projet d'avenir simple | QCM, texte à trous, production écrite | Rédigé (brouillon) | Texte original |

> Ces 4 unités sont un **point de départ réel**, une par niveau du MVP — pas des exemples fictifs. Chaque niveau doit encore atteindre le seuil de 6 unités validées (voir section 5) ; les unités supplémentaires restent à produire sur le même modèle, à l'aide du gabarit `16-gabarit-auteur-exercice.md`.

### 3.2 Contenu détaillé des 4 unités du MVP

#### U-6E-01 — « Ich stelle mich vor » (6ème, A1)

```
Hallo! Ich heiße Aïcha. Ich bin zwölf Jahre alt. Ich wohne in
Yaoundé, in Kamerun. Ich gehe in die sechste Klasse. Meine Familie
ist groß: Ich habe einen Bruder und zwei Schwestern. Mein
Lieblingsfach ist Deutsch. Ich spiele gern Fußball mit meinen
Freunden. Am Wochenende helfe ich meiner Mutter zu Hause.
```

**Glossaire** : heißen (s'appeler) · wohnen (habiter) · die Klasse (la classe) · die Familie (la famille) · der Bruder (le frère) · die Schwester (la sœur) · das Lieblingsfach (la matière préférée) · spielen (jouer) · helfen (aider)

**Exercice de compréhension (QCM)** : « Wie alt ist Aïcha? » → a) zehn ☐ b) zwölf ☑ c) vierzehn ☐

**Exercice d'écriture guidée** : « Stelle dich vor: Wie heißt du? Wie alt bist du? Wo wohnst du? » *(3 à 4 phrases attendues, niveau A1)*

#### U-5E-01 — « Meine Woche » (5ème, A1 consolidation)

```
Ich heiße Paul und ich bin dreizehn Jahre alt. Ich gehe in die
fünfte Klasse. Meine Woche ist immer interessant. Montags und
mittwochs habe ich Deutschunterricht, das mag ich sehr. Donnerstags
spiele ich Basketball mit meinem besten Freund. Am Wochenende gehe
ich oft mit meiner Familie zur Kirche, und danach besuchen wir
meine Großeltern. Mein Lieblingstag ist der Samstag, weil ich dann
keine Hausaufgaben mache.
```

**Glossaire** : die Woche (la semaine) · der Unterricht (les cours) · best- (meilleur) · die Kirche (l'église) · die Großeltern (les grands-parents) · der Lieblingstag (le jour préféré) · die Hausaufgaben (les devoirs)

**Exercice QCM** : « Wann spielt Paul Basketball? » → a) montags ☐ b) donnerstags ☑ c) samstags ☐

**Exercice vrai/faux** : « Paul geht am Samstag zur Schule. » → Faux

**Exercice d'écriture guidée** : « Beschreibe deine Woche: Was machst du montags, donnerstags und am Wochenende? » *(4 à 6 phrases attendues, niveau A1/A1+, subordonnée « weil » encouragée mais non exigée)*

> Note de conception : chaque unité du MVP introduit un personnage différent (Aïcha, Paul, Divine, Serge — voir 3.2), pour représenter la diversité des élèves camerounais plutôt qu'un narrateur unique récurrent.

#### U-4E-01 — « Ein Tag in Yaoundé » (4ème, A2)

```
Ich heiße Divine. Jeden Morgen stehe ich um sechs Uhr auf. Ich
wasche mich und ziehe meine Schuluniform an. Um sieben Uhr esse ich
Frühstück mit meiner Familie: Brot, Ei und Tee. Dann gehe ich zu
Fuß zur Schule, das dauert zwanzig Minuten. Der Unterricht beginnt
um acht Uhr und endet um vierzehn Uhr. Nach der Schule spiele ich
mit meinen Freunden auf dem Marktplatz Fußball. Am Abend habe ich
meiner Großmutter beim Kochen geholfen. Wir haben zusammen zu Abend
gegessen und über den Tag gesprochen. Danach habe ich meine
Hausaufgaben gemacht und bin früh ins Bett gegangen.
```

**Glossaire** : aufstehen (se lever) · die Schuluniform (l'uniforme scolaire) · der Unterricht (les cours) · der Marktplatz (la place du marché) · helfen (aider) · das Abendessen (le dîner) · die Hausaufgaben (les devoirs)

**Exercice texte à trous** : « Ich ___ (aufstehen) um sechs Uhr. » *(réponse : stehe ... auf)*

**Exercice d'écriture guidée** : « Beschreibe deinen Tag: Was machst du morgens, mittags und abends? » *(6 à 8 phrases attendues, niveau A2, Perfekt requis pour au moins 2 actions passées)*

#### U-3E-01 — « Meine Sommerferien in Kribi » (3ème, A2 consolidation — niveau BEPC)

```
Ich heiße Serge und ich bin vierzehn Jahre alt. Letzten Sommer bin
ich mit meiner Familie nach Kribi gefahren. Das war meine erste
große Reise ans Meer. Wir sind mit dem Auto gefahren, und die
Fahrt hat ungefähr vier Stunden gedauert. In Kribi haben wir ein
kleines Haus am Strand gemietet.

Jeden Tag sind wir früh aufgestanden und haben im Meer gebadet.
Nachmittags haben wir frischen Fisch gegessen, und meine Mutter hat
mit den Fischern gesprochen. Einmal haben wir auch die Wasserfälle
von Kribi besucht – das Wasser fällt direkt ins Meer, das war
wirklich beeindruckend!

Am Abend haben wir oft am Strand gesessen und über unsere Zukunft
gesprochen. Ich möchte später Arzt werden, weil ich anderen
Menschen helfen möchte. Diese Ferien waren unvergesslich, und ich
hoffe, dass wir nächstes Jahr wieder dorthin fahren.
```

**Glossaire** : die Reise (le voyage) · das Meer (la mer) · mieten (louer) · der Strand (la plage) · aufstehen (se lever) · baden (se baigner) · der Fischer (le pêcheur) · der Wasserfall (la chute d'eau) · beeindruckend (impressionnant) · die Zukunft (l'avenir) · unvergesslich (inoubliable)

**Exercice QCM** : « Wohin ist Serge im Sommer gefahren? » → a) Douala ☐ b) Kribi ☑ c) Limbé ☐

**Exercice texte à trous** : « Serge und seine Familie ___ (fahren) letzten Sommer nach Kribi. » *(réponse : sind ... gefahren)*

**Exercice de production écrite** : « Erzähle von deinen letzten Ferien: Wohin bist du gefahren? Was hast du gemacht? » *(niveau A2/BEPC, Perfekt étendu attendu, 8 à 10 phrases)*

> Note de conception : Serge est un personnage distinct des trois autres unités du MVP (Aïcha en 6e, Paul en 5e, Divine en 4e) — quatre personnages différents pour les quatre niveaux du collège, afin de représenter une diversité de prénoms et de profils d'élèves plutôt qu'un fil narratif unique.

### 3.3 Unités réservées (développement ultérieur — lycée)

Ces deux unités avaient été rédigées avant le resserrement du MVP au collège. Elles sont conservées ici, hors périmètre actuel, pour ne pas perdre ce travail — à reprendre lors du développement du lycée (2nde à Terminale).

| ID Unité | Niveau scolaire | Niveau GeR | Statut du contenu | Statut des droits |
|---|---|---|---|---|
| U-2NDE-01 | 2nde | B1 | Rédigé (brouillon) — hors MVP | Texte original |
| U-TLE-01 | Terminale | B1+/B2 | Rédigé (brouillon) — hors MVP | Texte original |

<details>
<summary>U-2NDE-01 — « Warum ist die Natur wichtig? » (2nde, B1) — contenu complet</summary>

```
Die Natur ist für uns Kameruner sehr wichtig, weil unser Land viele
Wälder, Flüsse und Tiere hat. Leider werden viele Bäume gefällt,
weil die Menschen Holz zum Bauen und zum Kochen brauchen. Das ist
ein großes Problem, denn ohne Bäume verändert sich das Klima, und
die Tiere verlieren ihren Lebensraum.

Meiner Meinung nach sollten wir mehr Bäume pflanzen und die Natur
besser schützen. Wenn jede Familie einen Baum pflanzt, kann sich
viel verändern. In meiner Schule haben wir letztes Jahr ein Projekt
gemacht: Wir haben zusammen mit unserem Lehrer fünfzig Bäume
gepflanzt.

Ich glaube, dass junge Menschen eine wichtige Rolle spielen können,
weil wir die Zukunft unseres Landes sind. Deshalb finde ich es
wichtig, dass wir in der Schule mehr über Umweltschutz lernen.
```

**Glossaire** : der Wald (la forêt) · fällen (abattre) · der Lebensraum (l'habitat) · pflanzen (planter) · der Umweltschutz (la protection de l'environnement)

</details>

<details>
<summary>U-TLE-01 — « Alte Geschichten, neue Zeiten » (Terminale, B1+/B2) — contenu complet</summary>

```
Es gibt in Kamerun viele alte Geschichten und Sprichwörter, die von
Generation zu Generation weitergegeben werden. Diese Geschichten
erzählen oft von Tieren, die klug oder listig sind, und sie
enthalten eine moralische Lehre für die Zuhörer.

Man könnte sich fragen, ob solche traditionellen Erzählungen in
einer modernen, digitalen Welt noch eine Bedeutung haben. Manche
Jugendliche meinen, dass diese Geschichten altmodisch seien und
nichts mehr mit ihrem Alltag zu tun hätten. Andere sind der
Meinung, dass gerade diese Erzählungen wichtige Werte wie
Ehrlichkeit, Mut und Gemeinschaftssinn vermitteln – Werte, die auch
heute noch gültig sind.

Wenn man genauer hinschaut, erkennt man, dass viele europäische
Märchen, wie die der Brüder Grimm, ähnliche Themen behandeln: den
Kampf zwischen Gut und Böse, die Bedeutung der Familie, die
Konsequenzen des eigenen Handelns.

Ich bin der Ansicht, dass wir diese Geschichten – ob afrikanisch
oder europäisch – bewahren sollten, weil sie uns helfen, über
unser eigenes Verhalten nachzudenken.
```

**Glossaire** : das Sprichwort (le proverbe) · weitergeben (transmettre) · listig (rusé) · die Lehre (la leçon) · altmodisch (démodé) · der Gemeinschaftssinn (l'esprit communautaire) · bewahren (préserver)

</details>

### 3.4 Colonnes à renseigner pour chaque unité réelle

- **ID Unité** : identifiant stable, utilisé comme clé de pré-population Room (`unite_id`) — convention : `U-<NIVEAU>-<NN>` (ex. `U-5E-02`, `U-3E-02`)
- **Niveau scolaire** : 6ème / 5ème / 4ème / 3ème *(MVP actuel)* — 2nde / 1ère / Terminale *(ultérieur)*
- **Niveau GeR** : A1, A2, B1, B2… selon la proposition de correspondance (section 2), à ajuster si besoin
- **Chapitre *Ihr und Wir Plus*** : référence exacte (numéro de chapitre/leçon) — **champ à compléter par toi**, je n'ai pas le contenu du manuel
- **Œuvre/extrait littéraire support** : titre, auteur, longueur approximative, source
- **Objectifs d'apprentissage** : formulés en termes de compétence écrite (aligné sur le sujet de mémoire)
- **Type d'exercices** : QCM / texte à trous / vrai-faux / production écrite guidée / libre
- **Statut du contenu** : `À rédiger` / `Rédigé (brouillon)` / `Relu` / `Validé`
- **Statut des droits** : `À vérifier` / `Domaine public` / `Autorisation obtenue` / `Texte original` — conformément à l'approche hybride tranchée en **ADR-006**

## 4. Processus de validation d'une unité

1. Remplir la ligne de la matrice (contenu + métadonnées)
2. Vérifier le statut des droits (domaine public, autorisation éditeur, ou texte original)
3. Faire relire le texte allemand par un locuteur natif ou l'encadrant académique — **obligatoire pour les 4 unités du MVP ci-dessus**, rédigées par un modèle de langage et non encore relues par un humain
4. Faire relire l'alignement GeR/curriculum par l'encadrant académique si possible
5. Passer le statut à `Validé`
6. Seules les unités `Validé` sont éligibles à la pré-population Room (Mission A4)

## 5. Portée du contenu pour le MVP (Cycle DBR 1) — ADR-008

**Décision tranchée** : le MVP couvre l'intégralité du **collège (6e, 5e, 4e, 3e)**, soit une bande CECR homogène A1 → A2. Le lycée (2nde, 1ère, Terminale — B1 à B2) est explicitement reporté à un développement ultérieur, probablement en Cycle DBR 2.

| Portée | Décision |
|---|---|
| Niveaux scolaires couverts au MVP | 6ème, 5ème, 4ème, 3ème (collège complet) |
| Niveaux GeR couverts au MVP | A1 (6e, 5e), A2 (4e, 3e) |
| Seuil minimal d'unités **validées** par niveau retenu | 5 unités par niveau (soit 20 unités au total) |
| Niveaux/unités reportés après le pilote | 2nde, 1ère, Terminale (lycée complet — développement ultérieur, 2 unités brouillon déjà réservées en section 3.3) |

### Suivi du seuil minimal (à tenir à jour à chaque sprint de contenu)

| Niveau scolaire | Niveau GeR | Unités `Validé` actuelles | Unités `Rédigé (brouillon)` actuelles | Seuil cible | Écart |
|---|---|---|---|---|---|
| 6ème | A1 | 0 | 1 (U-6E-01) | 5 | 4 |
| 5ème | A1 (consolidation) | 0 | 1 (U-5E-01) | 5 | 4 |
| 4ème | A2 | 0 | 1 (U-4E-01) | 5 | 4 |
| 3ème | A2 (consolidation) | 0 | 1 (U-3E-01) | 5 | 4 |

> Ce tableau doit être revu à chaque checklist hebdomadaire (`05-checklist-quotidienne.md`) tant que le seuil n'est pas atteint sur les 4 niveaux du MVP — c'est le principal levier de mitigation du risque R-07.

## 6. Suivi des mises à jour de contenu

Toute modification de cette cartographie après le début de la pré-population Room doit être versionnée (voir `14-charte-versionnage-contenu.md`) et répercutée dans les migrations Room si le schéma est impacté.
